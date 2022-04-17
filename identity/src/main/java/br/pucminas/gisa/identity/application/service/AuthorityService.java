package br.pucminas.gisa.identity.application.service;

import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import br.pucminas.gisa.identity.application.domain.Authority;
import br.pucminas.gisa.identity.application.repository.AuthorityRepository;

@Singleton
public class AuthorityService {

    private final AuthorityRepository repository;

    @Inject
    public AuthorityService(final AuthorityRepository repository) {
        this.repository = repository;
    }

    public List<Authority> findByProfileId(final UUID profileId) {
        return repository.findByProfileId(profileId);
    }
}
