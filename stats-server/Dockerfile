# Используем официальный образ Maven для сборки
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
# Базовый образ с Java
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]