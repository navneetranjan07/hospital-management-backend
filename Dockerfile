# --- Stage 1: Build Stage ---
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy everything
COPY . .

# Give mvnw executable permissions
RUN chmod +x mvnw

# Build the jar
RUN ./mvnw clean package -DskipTests

# --- Stage 2: Run Stage ---
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Render uses dynamic PORT, so expose any (optional)
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
