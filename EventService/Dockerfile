FROM java:8
VOLUME /tmp
ADD build/libs/EventService.jar EventService.jar
EXPOSE 8080
RUN bash -c 'touch /EventService.jar'
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongodb/locationService", "-Djava.security.egd=file:/dev/./urandom","-jar","/EventService.jar"]