package br.pucminas.gisa.api.gateway.application.presentation;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import br.pucminas.gisa.api.gateway.common.command.CreateIdentity;
import br.pucminas.gisa.api.gateway.common.command.GetIdentityToken;
import br.pucminas.gisa.api.gateway.infrastructure.client.IdentityClient;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import reactor.core.publisher.Mono;

@Validated
@Controller("/identities")
public class IdentityResource {

    private final IdentityClient client;

    @Inject
    public IdentityResource(final IdentityClient client) {
        this.client = client;
    }

    @SingleResult
    @Post(uri = "/auth")
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    public Mono<HttpResponse<?>> authenticate(@Body @Valid final GetIdentityToken command) {
        return Mono.from(client.authenticate(command));
    }

    @Get
    @SingleResult
    @Secured({"ROLE_ADMIN"})
    public Mono<HttpResponse<?>> findAll() {
        return Mono.from(client.findAll());
    }

    @Post
    @SingleResult
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Mono<HttpResponse<?>> create(@Valid @Body final CreateIdentity command) {
        return Mono.from(client.create(command));
    }
}
