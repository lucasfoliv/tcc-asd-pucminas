package br.pucminas.gisa.api.gateway.infrastructure.client;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.multipart.MultipartBody;
import reactor.core.publisher.Mono;

@Client(id = "receiver")
public interface DataReceiverClient {

    @SingleResult
    @Post(uri = "/api/worksheets/uploads", produces = MediaType.MULTIPART_FORM_DATA)
    Mono<HttpResponse<?>> upload(@Body final MultipartBody file);
}
