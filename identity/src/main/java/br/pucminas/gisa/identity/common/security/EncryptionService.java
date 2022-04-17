package br.pucminas.gisa.identity.common.security;

import javax.validation.constraints.NotBlank;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;

import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
@Requires(beans = PasswordEncoder.class)
public class EncryptionService {

    private final PasswordEncoder encoder;

    @Inject
    public EncryptionService(final PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String encode(@NotBlank @NonNull final String secret) {
        return encoder.encode(secret);
    }

    public boolean matches(@NotBlank @NonNull final String secret, @NotBlank @NonNull final String hash) {
        return encoder.matches(secret, hash);
    }
}
