package br.pucminas.gisa.mic.registry.infrastructure.data.migration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.runtime.event.annotation.EventListener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ValidationOptions;

import org.bson.Document;
import org.bson.conversions.Bson;

import br.pucminas.gisa.mic.registry.configuration.MigrationConfig;
import br.pucminas.gisa.mic.registry.infrastructure.data.migration.model.ChangeLog;
import br.pucminas.gisa.mic.registry.infrastructure.data.migration.model.ChangeSet;

@Singleton
@Requires(beans = MongoDatabase.class)
public class MigrationRunner {

    private final MongoDatabase database;

    @Inject
    public MigrationRunner(final MongoDatabase database) {
        this.database = database;
    }

    @EventListener
    public void onStartupCompleted(final StartupEvent event) throws IOException {
        final BeanContext context = event.getSource();
        final MigrationConfig configuration = context.getBean(MigrationConfig.class);
        final ObjectMapper mapper = context.getBean(ObjectMapper.class);

        try (final InputStream stream = loadChangelogResource(configuration.changelog())) {
            final List<String> collectionNames = database.listCollectionNames().into(new ArrayList<>());
            final ChangeLog changelog = mapper.readValue(stream, new TypeReference<>() {});

            changelog.changeSet().forEach(it -> createCollection(it, collectionNames));
        }
    }

    private void createCollection(final ChangeSet changeSet, final List<String> collectionNames) {
        final boolean collectionExists = collectionNames.stream().anyMatch(it -> changeSet.collectionName().equals(it));

        if (!collectionExists) {
            final ValidationOptions validationOptions = new ValidationOptions();

            validationOptions.validator(new Document(changeSet.validator()));
            validationOptions.validationAction(changeSet.validationAction());
            validationOptions.validationLevel(changeSet.validationLevel());

            final CreateCollectionOptions createCollectionOptions =
                new CreateCollectionOptions().validationOptions(validationOptions);

            database.createCollection(changeSet.collectionName(), createCollectionOptions);

            final MongoCollection<Document> collection = database.getCollection(changeSet.collectionName());
            final List<IndexModel> indexModels = changeSet.indexes()
                .stream()
                .map(MigrationRunner::createIndexModel)
                .toList();

            collection.createIndexes(indexModels);
        }
    }

    private static IndexModel createIndexModel(final Map<String, Object> indexStruct) {
        final String indexField = String.valueOf(indexStruct.get("field"));
        final String indexName = String.join("_", indexField, "idx");
        final IndexOptions indexOptions = new IndexOptions().name(indexName);

        if (indexStruct.containsKey("unique")) {
            final boolean uniqueValue = (boolean) indexStruct.get("unique");
            indexOptions.unique(uniqueValue);
        }

        final int indexType = (int) indexStruct.get("type");

        final Bson indexKeys = switch (indexType) {
            case 1 -> Indexes.ascending(indexField);
            case -1 -> Indexes.descending(indexField);
            default -> throw new IllegalArgumentException("Invalid index type. Must be 1 or -1.");
        };
        return new IndexModel(indexKeys, indexOptions);
    }

    private static InputStream loadChangelogResource(final String changelogPath) {
        final ResourceResolver resourceResolver = new ResourceResolver();
        final Optional<InputStream> optionalResourceStream = resourceResolver.getResourceAsStream(changelogPath);
        return optionalResourceStream.orElse(InputStream.nullInputStream());
    }
}
