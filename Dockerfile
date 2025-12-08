# -------- Stage 1 : Build the JAR --------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy all files
COPY . .

# Give permission to mvnw
RUN chmod +x mvnw

# Build the Spring Boot JAR
RUN ./mvnw clean package -DskipTests

# -------- Stage 2 : Run the JAR --------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built JAR from stage 1
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
