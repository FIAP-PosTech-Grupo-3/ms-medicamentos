# Dockerfile simples para ms-medicamentos
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o JAR já buildado
COPY target/ms-medicamentos-0.0.1.jar app.jar

# Cria usuário não-root
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
