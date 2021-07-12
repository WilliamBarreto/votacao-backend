FROM amazoncorretto:11-alpine-jdk
MAINTAINER William Barreto
COPY target/votacao-0.0.1-SNAPSHOT.jar votacao-1.0.0.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","-Duser.timezone=GMT-3","-Duser.language=pt","-Duser.country=BR","votacao-1.0.0.jar"]