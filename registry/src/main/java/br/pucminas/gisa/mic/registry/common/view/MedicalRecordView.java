package br.pucminas.gisa.mic.registry.common.view;

import java.time.LocalDateTime;

import io.micronaut.core.annotation.Introspected;
import org.bson.types.ObjectId;

@Introspected
public record MedicalRecordView(
    ObjectId id,
    String recordId,
    String associateTaxpayerId,
    LocalDateTime createdAt
) {}
