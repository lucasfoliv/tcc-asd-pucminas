package br.pucminas.gisa.mic.registry.infrastructure.data;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import br.pucminas.gisa.mic.registry.configuration.DataSourceConfig;

@Factory
@Requires(beans = DataSourceConfig.class)
public class DatabaseFactory {

    private final DataSourceConfig configuration;

    @Inject
    public DatabaseFactory(final DataSourceConfig configuration) {
        this.configuration = configuration;
    }

    @Singleton
    public MongoDatabase database(final MongoClient mongoClient) {
        return mongoClient.getDatabase(configuration.database());
    }
}
