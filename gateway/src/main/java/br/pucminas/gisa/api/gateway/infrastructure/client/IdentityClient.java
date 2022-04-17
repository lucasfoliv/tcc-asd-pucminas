package br.pucminas.gisa.api.gateway.infrastructure.client;

import jakarta.validation.Valid;

import br.pucminas.gisa.api.gateway.common.command.CreateIdentity;
import br.pucminas.gisa.api.gateway.common.command.GetIdentityToken;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client(id = "identity")
public interface IdentityClient {

    @SingleResult
    @Post(uri = "/api/token")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    Mono<HttpResponse<?>> authenticate(@Valid @Body final GetIdentityToken command);

    @SingleResult
    @Get(uri = "/api/users")
    Mono<HttpResponse<?>> findAll();

    @Post
    @SingleResult
    @Get(uri = "/api/users")
    Mono<HttpResponse<?>> create(@Valid @Body final CreateIdentity command);
}
