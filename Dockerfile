
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
# Teeme Maveni faili käivitatavaks ja ehitame .jar faili
RUN chmod +x mvnw && ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Kopeerime ainult valmis ehitatud .jar faili
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]