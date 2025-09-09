# Базовый образ с Java
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY stats-server-app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
