FROM ibm-semeru-runtimes:open-17-jdk

LABEL description="GISA API Gateway Microservice"

ARG USER

RUN adduser --disabled-password --gecos '' $USER && \
    mkdir deployments && \
    chown -R $USER deployments

USER $USER

COPY --chown=$USER target/gateway-*.jar /deployments/gateway.jar
COPY --chown=$USER scripts/docker-entrypoint.sh /

RUN chmod +x /docker-entrypoint.sh

WORKDIR /deployments

EXPOSE 8080

ENTRYPOINT ["/docker-entrypoint.sh"]
