package br.pucminas.gisa.mcd.receiver.infrastructure.messaging;

import java.util.Map;

import jakarta.inject.Inject;

import br.pucminas.gisa.mcd.receiver.common.event.InformationRegistryConsumedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.messaging.annotation.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class InformationRegistryConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(InformationRegistryConsumer.class);

    private final ApplicationEventPublisher<InformationRegistryConsumedEvent> eventPublisher;

    @Inject
    public InformationRegistryConsumer(final ApplicationEventPublisher<InformationRegistryConsumedEvent> eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Topic("${app.messaging.topics.medical-records}")
    public void receive(@MessageBody final Map<String, Object> message) {
        LOG.info("Medical Record message received: {}", message);

        final InformationRegistryConsumedEvent event = new InformationRegistryConsumedEvent(message);

        eventPublisher.publishEvent(event);
    }
}
