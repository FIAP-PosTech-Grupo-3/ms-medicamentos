# Dockerfile para ms-medicamentos
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven
COPY target/ms-medicamentos-0.0.1-SNAPSHOT.jar app.jar

# Copia o arquivo de propriedades, se necessário
COPY src/main/resources/application.properties ./

# Porta exposta
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]
