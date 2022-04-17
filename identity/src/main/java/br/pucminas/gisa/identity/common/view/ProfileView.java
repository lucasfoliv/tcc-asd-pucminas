package br.pucminas.gisa.identity.common.view;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import br.pucminas.gisa.identity.common.typing.IdentityStatus;
import io.micronaut.core.annotation.Introspected;

@Introspected
public record ProfileView(
    @NotBlank String name,
    @NotNull IdentityStatus status,
    @NotEmpty Set<String> authorities
) {}
