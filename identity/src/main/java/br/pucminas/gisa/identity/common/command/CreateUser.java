package br.pucminas.gisa.identity.common.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record CreateUser(
    @NotBlank String email,
    @NotBlank @Size(min = 7) String secret,
    @NotBlank String profile
) {}
