package br.pucminas.gisa.mic.registry.infrastructure.client;

import java.util.Map;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client(id = "middleware")
public interface MiddlewareClient {

    @SingleResult
    @Post(uri = "/mcd/receivers")
    Mono<HttpResponse<?>> sendMedicalRecord(@Body final Map<String, Object> medicalRecord);
}
