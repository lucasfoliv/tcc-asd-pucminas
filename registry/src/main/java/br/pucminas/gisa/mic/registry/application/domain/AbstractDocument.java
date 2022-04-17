package br.pucminas.gisa.mic.registry.application.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.micronaut.data.annotation.*;
import org.bson.types.ObjectId;

abstract class AbstractDocument implements Serializable {

    @Id
    @GeneratedValue
    protected ObjectId id;

    @Version
    @MappedProperty("optlock")
    protected Long version;

    @DateCreated
    protected LocalDateTime createdAt;

    @DateUpdated
    protected LocalDateTime updatedAt;

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
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
