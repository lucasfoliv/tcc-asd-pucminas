package br.pucminas.gisa.mic.registry.application.service;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import br.pucminas.gisa.mic.registry.application.domain.MedicalRecord;
import br.pucminas.gisa.mic.registry.application.repository.MedicalRecordRepository;
import br.pucminas.gisa.mic.registry.application.service.mapper.MedicalRecordMapper;
import br.pucminas.gisa.mic.registry.common.command.CreateMedicalRecord;
import br.pucminas.gisa.mic.registry.common.event.MedicalRecordCreatedEvent;
import br.pucminas.gisa.mic.registry.common.view.MedicalRecordView;
import io.micronaut.context.event.ApplicationEventPublisher;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MedicalRecordService {

    private static final Logger LOG = LoggerFactory.getLogger(MedicalRecordService.class);

    private final MedicalRecordRepository repository;
    private final ApplicationEventPublisher<MedicalRecordCreatedEvent> eventPublisher;
    private final MedicalRecordMapper mapper;

    @Inject
    public MedicalRecordService(final MedicalRecordRepository repository,
                                final ApplicationEventPublisher<MedicalRecordCreatedEvent> eventPublisher,
                                final MedicalRecordMapper mapper) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.mapper = mapper;
    }

    public List<MedicalRecordView> findAll() {
        return IterableUtils.toList(repository.findAll())
                .stream()
                .map(mapper::toView)
                .toList();
    }

    public MedicalRecordView create(final CreateMedicalRecord command) {
        final MedicalRecord medicalRecord = repository.save(mapper.toMedicalRecord(command));
        final MedicalRecordCreatedEvent event = new MedicalRecordCreatedEvent(medicalRecord);

        LOG.info("Medical Record successfully created: {}", medicalRecord);

        eventPublisher.publishEvent(event);

        return mapper.toView(medicalRecord);
    }
}
