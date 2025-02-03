FROM amazoncorretto:23@sha256:adf7ce17a8eae97c93bd1821bc3bf51ec424aa373ab02cc1c53c2689a5614426
ARG APP_NAME
ARG APP_VERSION
ENV VERSION=$APP_VERSION

LABEL org.opencontainers.image.source=https://github.com/creek-service/ks-aggregate-api-demo/tree/main/handle-occurrence-service

RUN yum update -y
RUN yum install -y tar lsof

RUN mkdir -p /opt/creek

COPY bin /bin
COPY log4j /log

COPY ${APP_NAME}-${APP_VERSION}.tar /opt/creek
WORKDIR /opt/creek
RUN tar xf ${APP_NAME}-${APP_VERSION}.tar
RUN ln -s ${APP_NAME}-${APP_VERSION} service

ENTRYPOINT ["/bin/run.sh"]