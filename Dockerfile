FROM openjdk:11
COPY target/trip_planner*.jar /usr/src/trip_planner.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/trip_planner.jar", "--spring.config.location=file:/opt/conf/application.properties"]
