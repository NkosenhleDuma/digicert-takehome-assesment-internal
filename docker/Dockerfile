# Using Zulu OpenJDK image: https://hub.docker.com/r/azul/zulu-openjdk
FROM azul/zulu-openjdk:17.0.10-jre-headless

WORKDIR /digicert

COPY ./target/digicert-booking-takehome-0.0.1-SNAPSHOT.jar digicert-booking.jar

EXPOSE 8080

CMD ["java", "-jar", "digicert-booking.jar"]