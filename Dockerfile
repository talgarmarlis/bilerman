FROM adoptopenjdk:11-jre-hotspot
MAINTAINER artatech.com
COPY target/bilerman.jar bilerman.jar
ENTRYPOINT ["java", "-jar", "bilerman.jar", "--spring.profiles.active=prod", "$ARGS"]
EXPOSE 8888