package br.pucminas.gisa.api.gateway.common.command;

import jakarta.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record GetIdentityToken(@NotBlank String username, @NotBlank String password) {}
