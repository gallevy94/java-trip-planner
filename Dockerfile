FROM openjdk:11
COPY target/trip-planner*.jar /usr/src/trip-planner.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/trip-planner.jar", "--spring.config.location=file:/opt/conf/application.properties"]
