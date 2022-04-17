package br.pucminas.gisa.mic.registry.application.presentation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.validation.Valid;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import br.pucminas.gisa.mic.registry.application.service.MedicalRecordService;
import br.pucminas.gisa.mic.registry.common.command.CreateMedicalRecord;
import br.pucminas.gisa.mic.registry.common.view.MedicalRecordView;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.annotation.Secured;
import io.micronaut.validation.Validated;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Validated
@Controller("/medicalRecords")
public class MedicalRecordResource {

    private final MedicalRecordService service;
    private final ExecutorService executor;

    @Inject
    public MedicalRecordResource(final MedicalRecordService service,
                                 @Named(TaskExecutors.IO) final ExecutorService executor) {
        this.service = service;
        this.executor = executor;
    }

    @Get
    @SingleResult
    @Secured({"ROLE_ADMIN", "ROLE_MEDICAL_RECORDS_FULL", "ROLE_MEDICAL_RECORDS_READONLY"})
    public Mono<MutableHttpResponse<List<MedicalRecordView>>> findAll() {
        return Mono.fromCallable(service::findAll)
                .map(HttpResponse::ok)
                .subscribeOn(Schedulers.fromExecutorService(executor));
    }

    @Post
    @SingleResult
    @Secured({"ROLE_ADMIN", "ROLE_MEDICAL_RECORDS_FULL"})
    public Mono<MutableHttpResponse<MedicalRecordView>> create(@Valid @Body final CreateMedicalRecord command) {
        return Mono.fromCallable(() -> service.create(command))
                .map(HttpResponse::created)
                .subscribeOn(Schedulers.fromExecutorService(executor));
    }
}
