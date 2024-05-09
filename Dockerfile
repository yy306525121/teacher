FROM openjdk:8-jre

MAINTAINER pdai

WORKDIR /

COPY teacher-admin/target/teacher-admin.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]