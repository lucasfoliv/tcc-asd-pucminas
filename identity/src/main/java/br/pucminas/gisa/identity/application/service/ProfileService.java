package br.pucminas.gisa.identity.application.service;

import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import br.pucminas.gisa.identity.application.domain.Profile;
import br.pucminas.gisa.identity.application.repository.ProfileRepository;

@Singleton
public class ProfileService {

    private final ProfileRepository repository;

    @Inject
    public ProfileService(final ProfileRepository repository) {
        this.repository = repository;
    }

    public Optional<Profile> findByName(final String profileName) {
        return repository.findByName(profileName);
    }
}
