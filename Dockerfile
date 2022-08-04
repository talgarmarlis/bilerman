FROM maven:3.8.6-jdk-11 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM adoptopenjdk:11-jre-hotspot
MAINTAINER artatech.com
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/bilerman.jar bilerman.jar
ENTRYPOINT ["java", "-jar", "bilerman.jar", "--spring.profiles.active=prod"]
EXPOSE 8888