package br.pucminas.gisa.identity.application.presentation;

import java.util.List;
import java.util.concurrent.ExecutorService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;

import br.pucminas.gisa.identity.application.service.UserService;
import br.pucminas.gisa.identity.common.command.CreateUser;
import br.pucminas.gisa.identity.common.view.UserView;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Validated
@Controller("/users")
public class UserResource {

    private final UserService service;
    private final ExecutorService executor;

    @Inject
    public UserResource(@Named(TaskExecutors.IO) final ExecutorService executor, final UserService service) {
        this.service = service;
        this.executor = executor;
    }

    @Get
    @SingleResult
    @Secured({"ROLE_ADMIN"})
    public Mono<MutableHttpResponse<List<UserView>>> findAll() {
        return Mono.fromCallable(service::findAll)
                .map(HttpResponse::ok)
                .subscribeOn(Schedulers.fromExecutorService(executor));
    }

    @Post
    @SingleResult
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Mono<MutableHttpResponse<UserView>> create(@Valid @Body final CreateUser command) {
        return Mono.fromCallable(() -> service.create(command))
                .flatMap(Mono::justOrEmpty)
                .map(HttpResponse::created)
                .defaultIfEmpty(HttpResponse.unprocessableEntity())
                .subscribeOn(Schedulers.fromExecutorService(executor));
    }
}
