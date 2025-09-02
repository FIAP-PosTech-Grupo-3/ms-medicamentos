#!/bin/bash

# Script para iniciar o MS Medicamentos com Docker

echo "🧹 Limpando volumes e containers antigos..."
docker compose down -v 2>/dev/null || true
docker system prune -f

echo "🔨 Fazendo build da aplicação..."
mvn clean package -DskipTests

echo "🐳 Iniciando containers..."
docker compose up --build -d

echo "⏳ Aguardando inicialização..."
sleep 10

echo "📊 Status dos containers:"
docker compose ps

echo "🚀 Aplicação disponível em: http://localhost:8080"
echo "📋 Health check: http://localhost:8080/actuator/health"
echo "📖 API Docs: src/main/resources/requests.http"

echo ""
echo "Para ver logs em tempo real:"
echo "docker compose logs -f"
echo ""
echo "Para parar:"
echo "docker compose down"
