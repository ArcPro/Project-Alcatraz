# Étape 1 : build Maven
FROM maven:3.8.5-openjdk-11 AS builder
WORKDIR /app

# 1. Copy pom + sources
COPY pom.xml .
COPY src ./src

# 2. Compile & pack
RUN mvn clean package -DskipTests

# Étape 2 : runtime
FROM openjdk:11-jre-slim
WORKDIR /app

# 3. Récupère le JAR construit
COPY --from=builder /app/target/evasion-alcatraz-1.0-SNAPSHOT.jar ./app.jar

# 4. Point d’entrée
ENTRYPOINT ["java", "-jar", "app.jar"]
