FROM openjdk:17-alpine AS build
WORKDIR /app
COPY pom.xml ./
COPY . .

# Install Maven in the build stage
RUN apk add --no-cache maven

# Run the build command (skipping tests)
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight OpenJDK image
FROM openjdk:17-alpine
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar


# Expose the port the application runs on
EXPOSE 8888

# Environment variables for Spring Boot application
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/BackendFileRouge?createDatabaseIfNotExist=true
ENV SPRING_DATASOURCE_USERNAME=root

ENV SPRING_JPA_SHOW_SQL=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SERVER_PORT=8888
ENV SPRING_LIQUIBASE_CHANGE_LOG=db/changelog/changelog-primary.xml

# Secret key and access token configuration
ENV SECRET_KEY=f7b5c19f433459acc6816901e4457b05c6186110a481d5b1a838aed92650946b
ENV REFRESH_TOKEN_EXPIRATION_TIME=864000000
ENV ACCESS_TOKEN_EXPIRATION_TIME=3600000

# Multipart configuration
ENV SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE=100MB
ENV SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE=100MB

# Additional configuration (optional)
CMD ["java", "-jar", "app.jar"]
