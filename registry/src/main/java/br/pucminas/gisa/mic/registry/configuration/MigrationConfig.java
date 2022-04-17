package br.pucminas.gisa.mic.registry.configuration;

import javax.validation.constraints.NotBlank;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

import static br.pucminas.gisa.mic.registry.configuration.ConfigProperties.*;

@Requires(property = MIGRATION_KEY)
@ConfigurationProperties(MIGRATION_KEY)
public record MigrationConfig(@NotBlank String changelog) {
}
