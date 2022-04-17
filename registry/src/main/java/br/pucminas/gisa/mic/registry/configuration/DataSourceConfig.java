package br.pucminas.gisa.mic.registry.configuration;

import javax.validation.constraints.NotBlank;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

import static br.pucminas.gisa.mic.registry.configuration.ConfigProperties.DATASOURCE_KEY;

@Requires(property = DATASOURCE_KEY)
@ConfigurationProperties(DATASOURCE_KEY)
public record DataSourceConfig(@NotBlank String database, @NotBlank String collection) {
}
