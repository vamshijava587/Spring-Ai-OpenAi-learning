# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -q    # cache dependencies
COPY src src
RUN ./mvnw package -DskipTests -q

# Run stage — smaller image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Non-root user for security
RUN addgroup -S medai && adduser -S medai -G medai
USER medai

EXPOSE 8082
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]