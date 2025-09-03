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

### 📊 API Medicamentos

#### Cadastro
- `POST /api/medicamentos` - Cadastrar medicamento

#### Buscas
- `GET /api/medicamentos/{id}` - Buscar por ID
- `GET /api/medicamentos` - Buscar todos (paginado)
- `GET /api/medicamentos/buscar?nome={nome}` - Buscar por nome (paginado)

#### Parâmetros de Paginação
- `page` - Número da página (padrão: 0)
- `size` - Tamanho da página (padrão: 20)
- `sortBy` - Campo para ordenação (padrão: id)
- `sortDirection` - Direção da ordenação: ASC/DESC (padrão: ASC)

#### Exemplos
```
GET /api/medicamentos?page=0&size=5&sortBy=nome&sortDirection=ASC
GET /api/medicamentos/buscar?nome=paracetamol&page=0&size=10
```

## 🧪 Testando

Use o arquivo `src/main/resources/requests.http` com a extensão REST Client do VS Code.

## ⚠️ Tratamento de Exceções

- **400 Bad Request** - Campos obrigatórios inválidos ou vazios
- **404 Not Found** - Medicamento não encontrado
- **422 Unprocessable Entity** - Erro de regra de negócio
- **500 Internal Server Error** - Erro interno do servidor

## 🛑 Parar os Serviços

```bash
docker-compose down
```

## 🗄️ Limpar Dados

```bash
docker-compose down -v  # Remove volumes também
```
