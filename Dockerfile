# Build stage
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copy pom.xml và tải dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy toàn bộ source code và build ứng dụng
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy file JAR từ build stage
COPY --from=build /app/target/Back-end-0.0.1-SNAPSHOT.jar .

# Thiết lập biến môi trường
ENV APP_PORT=8080

# Expose port
EXPOSE $APP_PORT

# Lệnh khởi chạy ứng dụng
CMD ["java", "-jar", "/app/Back-end-0.0.1-SNAPSHOT.jar"]
