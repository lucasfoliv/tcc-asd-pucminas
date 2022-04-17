package br.pucminas.gisa.identity.application.repository;

import java.util.Optional;
import java.util.UUID;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import br.pucminas.gisa.identity.application.domain.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, UUID> {

    @NonNull
    Optional<Profile> findByName(final String name);
}
