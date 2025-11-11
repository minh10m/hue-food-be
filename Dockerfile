# Build stage
FROM maven:3.8.4-eclipse-temurin-17 AS build
WORKDIR /app

# copy pom and download deps (offline)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy sources and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# Runtime stage (dùng Temurin OpenJDK 17)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Optional: tạo user không phải root
RUN addgroup --system app && adduser --system --ingroup app app
USER app

# Copy jar (ghi đè tên file thành app.jar để an toàn)
COPY --from=build --chown=app:app /app/target/*.jar /app/app.jar

# ENV / port
ARG APP_PORT=8080
ENV APP_PORT=${APP_PORT}
EXPOSE ${APP_PORT}

# Healthcheck (tùy chọn)
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s \
  CMD curl -f http://localhost:${APP_PORT}/actuator/health || exit 1

# Run
ENTRYPOINT ["java","-jar","/app/app.jar"]
