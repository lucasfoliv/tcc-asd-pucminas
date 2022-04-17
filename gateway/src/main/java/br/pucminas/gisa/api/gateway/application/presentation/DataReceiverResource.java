package br.pucminas.gisa.api.gateway.application.presentation;

import jakarta.inject.Inject;

import br.pucminas.gisa.api.gateway.infrastructure.client.DataReceiverClient;
import br.pucminas.gisa.api.gateway.common.helper.MultipartBodyHelper;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Mono;

@Controller("/receivers")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class DataReceiverResource {

    private final DataReceiverClient client;

    @Inject
    public DataReceiverResource(final DataReceiverClient client) {
        this.client = client;
    }

    @SingleResult
    @Secured({"ROLE_ADMIN", "ROLE_UPLOAD_FILE_FULL"})
    @Post(uri = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA)
    public Mono<HttpResponse<?>> upload(@Body final CompletedFileUpload file) {
        return Mono.from(client.upload(MultipartBodyHelper.of(file)));
    }
}
