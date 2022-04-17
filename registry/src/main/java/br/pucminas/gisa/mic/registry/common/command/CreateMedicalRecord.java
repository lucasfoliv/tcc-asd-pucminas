package br.pucminas.gisa.mic.registry.common.command;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record CreateMedicalRecord(
    @NotBlank String recordId,
    @NotBlank String associateTaxpayerId,
    @NotNull LocalDateTime attendanceDate,
    @NotBlank String attendanceType,
    @NotBlank String professionalName,
    @NotBlank String professionalRegistry,
    @NotBlank String medicalHistory,
    @NotNull Double weight,
    @NotNull Double imc,
    @NotNull Double heartRate,
    @NotNull Double bloodPressure,
    @NotNull Integer temperature
) {}
