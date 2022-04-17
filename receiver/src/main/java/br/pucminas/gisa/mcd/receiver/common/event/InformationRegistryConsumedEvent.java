package br.pucminas.gisa.mcd.receiver.common.event;

import java.util.Map;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record InformationRegistryConsumedEvent(Map<String, Object> medicalRecord) {
}
