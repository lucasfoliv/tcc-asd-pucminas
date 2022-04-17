package br.pucminas.gisa.mic.registry.application.service.mapper;

import br.pucminas.gisa.mic.registry.application.domain.MedicalRecord;
import br.pucminas.gisa.mic.registry.common.command.CreateMedicalRecord;
import br.pucminas.gisa.mic.registry.common.view.MedicalRecordView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface MedicalRecordMapper {

    MedicalRecord toMedicalRecord(final CreateMedicalRecord command);

    MedicalRecordView toView(final MedicalRecord source);
}
