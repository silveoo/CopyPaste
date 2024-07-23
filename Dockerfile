# Указываем базовый образ с Java 17
FROM maven:3.9.8-amazoncorretto-17-debian AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и зависимости в контейнер
COPY pom.xml .
COPY src ./src

# Скачиваем все зависимости и компилируем проект
RUN mvn clean package -DskipTests

# Указываем базовый образ для финального контейнера
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный jar файл из этапа сборки
COPY --from=build /app/target/*.jar ./app.jar

# Указываем команду для запуска приложения
CMD ["java", "-jar", "app.jar"]
