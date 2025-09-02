# MS Medicamentos - Guia de Execução

Este projeto está **100% containerizado** e pode ser executado sem nenhuma instalação local.

## 🚀 Execução Rápida

```bash
# Clone o repositório
git clone <repo-url>
cd ms-medicamentos/msdicamentos

# Execute tudo com Docker Compose
docker-compose up --build
```

## 📋 Pré-requisitos

- **Docker** instalado
- **Docker Compose** instalado

## 🔧 Recursos Incluídos

- ✅ **PostgreSQL** (banco de dados)
- ✅ **Flyway** (migrações automáticas)
- ✅ **Spring Boot** (aplicação)
- ✅ **Build automático** (não precisa Maven local)

## 🌐 Endpoints

Após iniciar, a API estará disponível em:
- **Base URL**: http://localhost:8080
- **Health Check**: http://localhost:8080/actuator/health
- **API Medicamentos**: http://localhost:8080/api/medicamentos

## 🧪 Testando

Use o arquivo `src/main/resources/requests.http` com a extensão REST Client do VS Code.

## 🛑 Parar os Serviços

```bash
docker-compose down
```

## 🗄️ Limpar Dados

```bash
docker-compose down -v  # Remove volumes também
```
