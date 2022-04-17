package br.pucminas.gisa.identity.application.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;

@MappedSuperclass
abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    protected UUID id;

    @Version
    @Column(name = "optlock")
    protected Long version;

    @DateCreated
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @DateUpdated
    @Column(nullable = false)
    protected LocalDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
