package br.pucminas.gisa.identity.application.security;

import java.util.List;
import java.util.concurrent.ExecutorService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;

import org.reactivestreams.Publisher;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import br.pucminas.gisa.identity.application.domain.Authority;
import br.pucminas.gisa.identity.application.domain.User;
import br.pucminas.gisa.identity.common.typing.FailureMessage;


@Singleton
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserAuthenticationService authenticationService;
    private final ExecutorService executor;

    @Inject
    public UserAuthenticationProvider(final UserAuthenticationService authenticationService,
                                      @Named(TaskExecutors.IO) final ExecutorService executor) {
        this.authenticationService = authenticationService;
        this.executor = executor;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable final HttpRequest<?> request,
                                                          final AuthenticationRequest<?, ?> credentials) {
        return Mono.fromCallable(() -> authenticationService.findByIdentity((String) credentials.getIdentity()))
            .flatMap(it -> it.map(user -> Mono.just(createResponse(user, credentials))).orElseGet(Mono::empty))
            .defaultIfEmpty(AuthenticationResponse.failure(FailureMessage.NotFound.getCause()))
            .subscribeOn(Schedulers.fromExecutorService(executor));
    }

    private AuthenticationResponse createResponse(final User user, final AuthenticationRequest<?, ?> credentials) {
        return authenticationService.validateSecurityDetails(user, (String) credentials.getSecret())
            .orElseGet(() -> createSuccessfulResponse(user));
    }

    private AuthenticationResponse createSuccessfulResponse(final User user) {
        final List<Authority> authorities = authenticationService.findAuthoritiesByProfileId(user.getProfile());
        final List<String> roles = authorities.stream().map(Authority::getName).toList();
        return AuthenticationResponse.success(user.getEmail(), roles);
    }
}
