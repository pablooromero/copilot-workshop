# ============================================
# STAGE 1: Builder - Compilación con Maven
# ============================================
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (cacheado si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación (skip tests para build más rápido)
RUN mvn clean package -DskipTests

# ============================================
# STAGE 2: Runtime - Imagen final optimizada
# ============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Metadatos de la imagen
LABEL maintainer="Accenture Sharks Team"
LABEL description="Aria - API REST de Excusas Tech con Google Gemini AI"
LABEL version="1.0.0"

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar JAR compilado desde stage builder
COPY --from=builder /app/target/*.jar app.jar

# Exponer puerto de la aplicación
EXPOSE 8080

# Variables de entorno por defecto (pueden ser sobrescritas)
ENV JAVA_OPTS="-Xms256m -Xmx512m" \
    SPRING_PROFILES_ACTIVE="default"

# Health check para Docker
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
