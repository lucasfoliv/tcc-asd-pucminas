package br.pucminas.gisa.mcd.receiver.application.presentation;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import br.pucminas.gisa.mcd.receiver.application.service.WorksheetService;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.annotation.Secured;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Controller("/worksheets")
public class WorksheetResource {

    private final WorksheetService service;
    private final ExecutorService executor;

    @Inject
    public WorksheetResource(final WorksheetService service,
                             @Named(TaskExecutors.IO) final ExecutorService executor) {
        this.service = service;
        this.executor = executor;
    }

    @SingleResult
    @Secured({"ROLE_ADMIN", "ROLE_UPLOAD_FILE_FULL"})
    @Post(uri = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA)
    public Mono<HttpResponse<Map<String, String>>> upload(final StreamingFileUpload file) {
        final Supplier<HttpResponse<Map<String, String>>> responseSupplier = () -> HttpResponse.accepted()
                .body(Map.of("message", "%s successfully uploaded".formatted(file.getFilename())));

        return Mono.fromCallable(() -> service.createFile(file))
                .flatMap(it -> Mono.from(file.transferTo(it)))
                .map(it -> responseSupplier.get())
                .subscribeOn(Schedulers.fromExecutorService(executor));
    }
}
