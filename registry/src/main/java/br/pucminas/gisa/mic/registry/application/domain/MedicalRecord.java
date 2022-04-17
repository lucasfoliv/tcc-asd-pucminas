package br.pucminas.gisa.mic.registry.application.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity("medicalRecords")
public class MedicalRecord extends AbstractDocument {

    private String recordId;
    private String associateTaxpayerId;
    private LocalDateTime attendanceDate;
    private String attendanceType;
    private String professionalName;
    private String professionalRegistry;
    private String medicalHistory;
    private Double weight;
    private Double imc;
    private Double heartRate;
    private Double bloodPressure;
    private Integer temperature;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(final String recordId) {
        this.recordId = recordId;
    }

    public String getAssociateTaxpayerId() {
        return associateTaxpayerId;
    }

    public void setAssociateTaxpayerId(final String associateTaxpayerId) {
        this.associateTaxpayerId = associateTaxpayerId;
    }

    public LocalDateTime getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(final LocalDateTime attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(final String attendanceType) {
        this.attendanceType = attendanceType;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(final String professionalName) {
        this.professionalName = professionalName;
    }

    public String getProfessionalRegistry() {
        return professionalRegistry;
    }

    public void setProfessionalRegistry(final String professionalRegistry) {
        this.professionalRegistry = professionalRegistry;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(final String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(final Double weight) {
        this.weight = weight;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(final Double imc) {
        this.imc = imc;
    }

    public Double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(final Double heartRate) {
        this.heartRate = heartRate;
    }

    public Double getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(final Double bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(final Integer temperature) {
        this.temperature = temperature;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final MedicalRecord medicalRecord)) {
            return false;
        }
        return recordId.equals(medicalRecord.recordId)
               && associateTaxpayerId.equals(medicalRecord.associateTaxpayerId)
               && attendanceDate.equals(medicalRecord.attendanceDate)
               && professionalRegistry.equals(medicalRecord.professionalRegistry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, associateTaxpayerId, attendanceDate, professionalRegistry);
    }

    @Override
    public String toString() {
        return "MedicalRecord{" + "id=" + id +
               ", recordId=" + recordId +
               ", associateTaxpayerId=" + associateTaxpayerId +
               ", attendanceDate=" + attendanceDate +
               ", attendanceType=" + attendanceType +
               ", professionalName=" + professionalName +
               ", professionalRegistry=" + professionalRegistry +
               ", medicalHistory=" + medicalHistory +
               ", weight=" + weight +
               ", imc=" + imc +
               ", heartRate=" + heartRate +
               ", bloodPressure=" + bloodPressure +
               ", temperature=" + temperature +
               "}";
    }
}
