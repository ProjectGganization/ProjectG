# Stage 1: Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY backend/pom.xml .
COPY backend/src src
COPY backend/mvnw .
COPY backend/.mvn .mvn
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jdk
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
