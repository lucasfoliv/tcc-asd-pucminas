package br.pucminas.gisa.mic.registry.application.repository;

import br.pucminas.gisa.mic.registry.application.domain.MedicalRecord;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

@MongoRepository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, ObjectId> {
}
