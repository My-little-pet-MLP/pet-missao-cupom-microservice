# Etapa de construção
FROM maven:3.3.4-openjdk-21 AS builder

WORKDIR /app
COPY . .

# Construir o binário da aplicação
RUN mvn clean package -DskipTests

# Etapa final
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copiar o JAR compilado da etapa de construção
COPY --from=builder /app/target/*.jar app.jar

# Copiar o arquivo .env se ele existir
# COPY .env .env

EXPOSE 9093

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]
