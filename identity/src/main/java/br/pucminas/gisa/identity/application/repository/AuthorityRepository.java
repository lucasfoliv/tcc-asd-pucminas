package br.pucminas.gisa.identity.application.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import br.pucminas.gisa.identity.application.domain.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, UUID> {

    @NonNull
    List<Authority> findByNameIn(final Set<String> names);

    @NonNull
    @Join(value = "profiles", alias = "p")
    @Query("SELECT a FROM Authority a JOIN a.profiles p WHERE p.id = :profileId")
    List<Authority> findByProfileId(final UUID profileId);
}
