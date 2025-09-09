# Базовый образ с Java
FROM eclipse-temurin:21-jre

# Рабочая директория в контейнере
WORKDIR /app

# Копируем jar-файл из директории target модуля stats-server-app
COPY stats-server-app/target/*.jar app.jar

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
