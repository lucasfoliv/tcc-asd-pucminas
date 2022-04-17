package br.pucminas.gisa.middleware.configuration;

public final class ConfigProperties {

    public static final String SERVER = "app.server";
    public static final String SERVER_CONTEXT_PATH = "{{" + SERVER + ".context-path}}";
    public static final String SERVER_PORT = "{{" + SERVER + ".port}}";

    public static final String ROUTES = "app.routes";
    public static final String MCD_ROUTES = ROUTES + ".mcd";
    public static final String MIT_ROUTES = ROUTES + ".mit";

    public static final String MCD_MESSAGING = MCD_ROUTES + ".messaging";
    public static final String MCD_MESSAGING_ROUTE_ID = "{{" + MCD_MESSAGING + ".route-id}}";
    public static final String MCD_MESSAGING_BROKER = MCD_MESSAGING + ".broker";
    public static final String MCD_MESSAGING_BROKER_HOST = "{{" + MCD_MESSAGING_BROKER + ".host}}";
    public static final String MCD_MESSAGING_BROKER_PORT = "{{" + MCD_MESSAGING_BROKER + ".port}}";
    public static final String MCD_MESSAGING_TOPIC = "{{" + MCD_MESSAGING + ".topic}}";
    public static final String MCD_MESSAGING_PATH = MCD_MESSAGING_TOPIC + "?brokers=" + MCD_MESSAGING_BROKER_HOST + ":" + MCD_MESSAGING_BROKER_PORT;

    public static final String MIT_API = MIT_ROUTES + ".api";
    public static final String MIT_ROUTE_ID = "{{" + MIT_API + ".route-id}}";

    public static final String MIT_API_BASE_ENDPOINT_PATH = "/api/mcd";
    public static final String MIT_DATA_RECEIVER_API_ENDPOINT = "/receivers";

    private ConfigProperties() {
        throw new AssertionError("Class %s cannot be instantiated".formatted(getClass().getSimpleName()));
    }
}
