package br.pucminas.gisa.api.gateway.infrastructure.client;

import br.pucminas.gisa.api.gateway.common.command.CreateMedicalRecord;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client(id = "registry")
public interface DataRegistryClient {

    @SingleResult
    @Get(uri = "/api/medicalRecords")
    Mono<HttpResponse<?>> findAll();

    @SingleResult
    @Post(uri = "/api/medicalRecords")
    Mono<HttpResponse<?>> create(@Body final CreateMedicalRecord command);
}
