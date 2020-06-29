FROM openliberty/open-liberty:kernel-java8-openj9-ubi

USER root

RUN ln -s /opt/ol/wlp/usr/servers /servers

USER 1001

COPY --chown=1001:0 \
    src/main/liberty/config \
    /config/

COPY --chown=1001:0 \
    target/validation-service.war \
    /config/apps

ENTRYPOINT ["/opt/ol/wlp/bin/server", "run"]

CMD ["defaultServer"]