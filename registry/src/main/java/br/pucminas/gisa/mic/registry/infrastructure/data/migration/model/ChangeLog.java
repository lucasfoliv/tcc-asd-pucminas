package br.pucminas.gisa.mic.registry.infrastructure.data.migration.model;

import java.util.List;
import javax.validation.constraints.NotEmpty;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record ChangeLog(@NotEmpty List<ChangeSet> changeSet) {
}
