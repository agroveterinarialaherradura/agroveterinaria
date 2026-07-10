# ---- Etapa de compilación ----
FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder

WORKDIR /build

# Copiar archivos de definición de dependencias
COPY pom.xml .
# Descargar dependencias (capa cacheable)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar la aplicación (omitir pruebas para acelerar, opcional)
RUN mvn clean package -DskipTests

# ---- Etapa de ejecución ----
FROM eclipse-temurin:17-jre-alpine

# Crear directorio de trabajo
WORKDIR /app

# Copiar el jar desde la etapa builder
COPY --from=builder /build/target/*.jar app.jar

# Exponer puerto (ajustar si tu app usa otro)
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]