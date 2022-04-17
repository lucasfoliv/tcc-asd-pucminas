package br.pucminas.gisa.identity.common.view;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import br.pucminas.gisa.identity.common.typing.IdentityStatus;
import io.micronaut.core.annotation.Introspected;

@Introspected
public record UserView(
    @NotNull UUID id,
    @NotBlank String email,
    @NotNull IdentityStatus status,
    @NotNull ProfileView profile
) {}
