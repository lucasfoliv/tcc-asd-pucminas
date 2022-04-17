package br.pucminas.gisa.api.gateway.application.presentation;

import jakarta.inject.Inject;

import br.pucminas.gisa.api.gateway.common.command.CreateMedicalRecord;
import br.pucminas.gisa.api.gateway.infrastructure.client.DataRegistryClient;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import reactor.core.publisher.Mono;

@Validated
@Controller("/registry")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class DataRegistryResource {

    private final DataRegistryClient client;

    @Inject
    public DataRegistryResource(final DataRegistryClient client) {
        this.client = client;
    }

    @SingleResult
    @Get(uri = "/medicalRecords")
    @Secured({"ROLE_ADMIN", "ROLE_MEDICAL_RECORDS_FULL", "ROLE_MEDICAL_RECORDS_READONLY"})
    public Mono<HttpResponse<?>> findAll() {
        return Mono.from(client.findAll());
    }

    @SingleResult
    @Post(uri = "/medicalRecords")
    @Secured({"ROLE_ADMIN", "ROLE_MEDICAL_RECORDS_FULL"})
    public Mono<HttpResponse<?>> create(@Body final CreateMedicalRecord command) {
        return Mono.from(client.create(command));
    }
}
