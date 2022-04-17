package br.pucminas.gisa.mic.registry.common.event;

import br.pucminas.gisa.mic.registry.application.domain.MedicalRecord;
import io.micronaut.core.annotation.Introspected;

@Introspected
public record MedicalRecordCreatedEvent(MedicalRecord medicalRecord) {
}
