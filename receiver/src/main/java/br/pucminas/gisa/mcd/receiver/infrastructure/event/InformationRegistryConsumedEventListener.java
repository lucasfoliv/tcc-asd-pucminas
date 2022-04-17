package br.pucminas.gisa.mcd.receiver.infrastructure.event;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import br.pucminas.gisa.mcd.receiver.common.event.InformationRegistryConsumedEvent;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.scheduling.TaskExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Singleton
public class InformationRegistryConsumedEventListener implements ApplicationEventListener<InformationRegistryConsumedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(InformationRegistryConsumedEventListener.class);
    private static final String DATASET_REPOSITORY_PATH = System.getProperty("java.io.tmpdir");
    private static final String DATASET_FILENAME = "dataset-%s.json";

    private final ExecutorService executor;
    private final ObjectWriter writer;

    @Inject
    public InformationRegistryConsumedEventListener(@Named(TaskExecutors.IO) final ExecutorService executor,
                                                    final ObjectMapper mapper) {
        this.writer = mapper.writer(new DefaultPrettyPrinter());
        this.executor = executor;
    }

    @Override
    public void onApplicationEvent(final InformationRegistryConsumedEvent event) {
        Mono.fromCallable(() -> eventHandler(event))
                .subscribeOn(Schedulers.fromExecutorService(executor))
                .subscribe();
    }

    private boolean eventHandler(final InformationRegistryConsumedEvent event) {
        try {
            final String timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
            final String filePath = String.join(File.separator, DATASET_REPOSITORY_PATH, DATASET_FILENAME.formatted(timestamp));
            final Map<String, Object> record = event.medicalRecord();

            LOG.info("Writing Medical Record data {} to dataset {}", record, filePath);

            writer.writeValue(new File(filePath), record);

            return true;
        } catch (IOException e) {
            LOG.error("Failed to parse incoming data", e);
            return false;
        }
    }
}
