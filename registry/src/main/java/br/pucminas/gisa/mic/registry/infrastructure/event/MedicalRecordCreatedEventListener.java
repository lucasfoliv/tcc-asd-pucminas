package br.pucminas.gisa.mic.registry.infrastructure.event;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import br.pucminas.gisa.mic.registry.application.domain.MedicalRecord;
import br.pucminas.gisa.mic.registry.common.event.MedicalRecordCreatedEvent;
import br.pucminas.gisa.mic.registry.infrastructure.client.MiddlewareClient;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Singleton
public class MedicalRecordCreatedEventListener implements ApplicationEventListener<MedicalRecordCreatedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(MedicalRecordCreatedEventListener.class);

    private final MiddlewareClient client;

    @Inject
    public MedicalRecordCreatedEventListener(final MiddlewareClient client) {
        this.client = client;
    }

    @Override
    public void onApplicationEvent(final MedicalRecordCreatedEvent event) {
        final Map<String, Object> medicalRecord = new LinkedHashMap<>();
        try {
            final BeanIntrospection<MedicalRecord> introspection = BeanIntrospection.getIntrospection(MedicalRecord.class);

            for (final BeanProperty<MedicalRecord, Object> property : introspection.getBeanProperties()) {
                final Object propertyValue = property.get(event.medicalRecord());
                if (propertyValue != null) {
                    medicalRecord.put(property.getName(), propertyValue);
                }
            }

            Mono.from(client.sendMedicalRecord(medicalRecord)).subscribe();
        } catch (Exception e) {
            LOG.error("Failed to send Medical Record data", e);
        }
    }
}
