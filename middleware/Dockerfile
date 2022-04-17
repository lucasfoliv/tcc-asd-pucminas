FROM ibm-semeru-runtimes:open-17-jdk

LABEL description="GISA Middleware Microservice"

ARG USER

ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

RUN adduser --disabled-password --gecos '' $USER && \
    mkdir deployments && \
    chown -R $USER deployments

USER $USER

COPY --chown=$USER target/*-runner.jar /deployments/middleware.jar
COPY --chown=$USER scripts/docker-entrypoint.sh /

RUN chmod +x /docker-entrypoint.sh

WORKDIR /deployments

EXPOSE 8080

ENTRYPOINT ["/docker-entrypoint.sh"]
