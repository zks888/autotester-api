FROM openjdk:8
USER root
WORKDIR /home
COPY ./apache-shardingsphere-elasticjob-3.1.0 elasticjob

EXPOSE 8088
RUN chmod a+x elasticjob/bin/start.sh
ENTRYPOINT elasticjob/bin/start.sh
