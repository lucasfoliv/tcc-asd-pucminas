package br.pucminas.gisa.identity.application.repository;

import java.util.Optional;
import java.util.UUID;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Join.Type;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import br.pucminas.gisa.identity.application.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    @NonNull
    @Join(value = "profile", type = Type.FETCH)
    Optional<User> findByEmail(final String email);

    boolean existsByEmail(final String email);
}
