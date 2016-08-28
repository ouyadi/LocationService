FROM java:8
MAINTAINER ouyadi
VOLUME /tmp
ADD build/libs/EventService.jar eventservice.jar
EXPOSE 8080
RUN bash -c 'touch /eventservice.jar'
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/locationService", "-Djava.security.egd=file:/dev/./urandom","-jar","/eventservice.jar"]