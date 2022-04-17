package br.pucminas.gisa.middleware.application.route;

import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import br.pucminas.gisa.middleware.configuration.ConfigProperties;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import static br.pucminas.gisa.middleware.configuration.ConfigProperties.*;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

@ApplicationScoped
public class DataScienceIntegrationRoute extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("platform-http")
                .bindingMode(RestBindingMode.json)
                .clientRequestValidation(true)
                .dataFormatProperty("perttyPrint", "true")
                .contextPath(SERVER_CONTEXT_PATH)
                .port(SERVER_PORT);

        rest(MIT_API_BASE_ENDPOINT_PATH)
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
                .post(MIT_DATA_RECEIVER_API_ENDPOINT)
                .route()
                .routeId(MIT_ROUTE_ID)
                .marshal().json(JsonLibrary.Jackson)
                .to(direct(MCD_MESSAGING_ROUTE_ID))
                .choice()
                .when(jsonpath("$.length()").isEqualTo(0))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Status.BAD_REQUEST.getStatusCode()))
                .setBody(bodyAs(Map.class))
                .endChoice()
                .otherwise()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Status.ACCEPTED.getStatusCode()))
                .setBody(bodyAs(Map.class))
                .endRest();

        from(direct(MCD_MESSAGING_ROUTE_ID))
                .routeId(MCD_MESSAGING_ROUTE_ID)
                .log("Message Published => ${body}")
                .to(kafka(ConfigProperties.MCD_MESSAGING_PATH));
    }
}
