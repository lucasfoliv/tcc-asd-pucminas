package br.pucminas.gisa.mic.registry.infrastructure.data.migration.model;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import com.mongodb.client.model.ValidationAction;
import com.mongodb.client.model.ValidationLevel;

@Introspected
public record ChangeSet(
    @NotBlank String collectionName,
    @NotEmpty Map<String, Object> validator,
    @NotEmpty List<Map<String, Object>> indexes,
    @NotNull @NonNull ValidationAction validationAction,
    @NotNull @NonNull ValidationLevel validationLevel) {
}
