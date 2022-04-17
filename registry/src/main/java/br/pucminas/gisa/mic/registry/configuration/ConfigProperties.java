package br.pucminas.gisa.mic.registry.configuration;

public final class ConfigProperties {

    public static final String APPLICATION_KEY = "app";

    public static final String DATASOURCE_KEY = APPLICATION_KEY + ".datasource";
    public static final String MIGRATION_KEY = APPLICATION_KEY + ".migration";

    private ConfigProperties() {
        throw new AssertionError("Class %s cannot be instantiated".formatted(getClass().getSimpleName()));
    }
}
