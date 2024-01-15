FROM openjdk:8-jre

MAINTAINER pdai

WORKDIR /

COPY target/teacher-admin.jar app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar"]
CMD ["app.jar", "--spring.profiles.active=pro"]

