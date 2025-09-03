# MS Medicamentos - Sistema de Gerenciamento

Este projeto é uma API REST para gerenciamento de medicamentos, desenvolvida com Spring Boot 3 e totalmente containerizada com Docker.

## Execução Rápida

```bash
# Clone o repositório
git clone <repo-url>
cd ms-medicamentos/msdicamentos

# Execute com Docker Compose
docker-compose up --build
```

## Pré-requisitos

- Docker instalado
- Docker Compose instalado

## Recursos Incluídos

- PostgreSQL (banco de dados)
- Flyway (migrações automáticas)
- Spring Boot 3 (aplicação)
- Build automático (Maven integrado)
- Swagger UI (documentação)

## Endpoints da API

Após iniciar, a API estará disponível em: **http://localhost:8080**

### Medicamentos

#### Operações CRUD
- `POST /api/medicamentos` - Cadastrar medicamento
- `GET /api/medicamentos/{id}` - Buscar por ID
- `PUT /api/medicamentos/{id}` - Atualizar medicamento
- `DELETE /api/medicamentos/{id}` - Deletar medicamento

#### Buscas com Paginação
- `GET /api/medicamentos` - Buscar todos (paginado)
- `GET /api/medicamentos/buscar?nome={nome}` - Buscar por nome (paginado)

#### Parâmetros de Paginação
- `page` - Número da página (padrão: 0)
- `size` - Tamanho da página (padrão: 20)
- `sortBy` - Campo para ordenação (padrão: id)
- `sortDirection` - Direção: ASC/DESC (padrão: ASC)

#### Exemplos de Uso
```
GET /api/medicamentos?page=0&size=5&sortBy=nome&sortDirection=ASC
GET /api/medicamentos/buscar?nome=paracetamol&page=0&size=10
```

## Estrutura do Banco de Dados

O sistema utiliza um modelo relacional com 3 tabelas principais criadas em uma única migração:

### medicamentos
Dados básicos dos medicamentos (sem informações de estoque)

### unidades_saude  
Cadastro de UBS, hospitais e clínicas

### medicamento_unidade_saude
Tabela associativa N:N que relaciona medicamentos com unidades de saúde, incluindo as quantidades em estoque por localização.

**Exemplo**: Um mesmo medicamento pode ter quantidades diferentes em cada UBS.

**Dados de exemplo** são inseridos automaticamente na primeira execução.

## Testes

### REST Client
Use o arquivo `src/main/resources/requests.http` com a extensão REST Client do VS Code para testar todos os endpoints.

### Swagger UI
Acesse a documentação interativa em:
- **Swagger UI**: http://localhost:8080/swagger-ui
- **API Docs JSON**: http://localhost:8080/api-docs

## Sistema de Logs

- **Console**: Logs básicos durante execução
- **Arquivo**: `logs/application.log` (rotacionado diariamente)
- **Nível**: INFO para aplicação, WARN para frameworks externos

## Tratamento de Erros

A API retorna códigos de status HTTP apropriados:

- **400 Bad Request** - Campos obrigatórios inválidos ou vazios
- **404 Not Found** - Medicamento não encontrado  
- **422 Unprocessable Entity** - Erro de regra de negócio
- **500 Internal Server Error** - Erro interno do servidor

## Comandos Úteis

### Parar os serviços
```bash
docker-compose down
```

### Limpar dados e volumes
```bash
docker-compose down -v
```

### Rebuild completo
```bash
docker-compose down -v
docker-compose up --build
```

## Tecnologias

- **Java 21**
- **Spring Boot 3.5.5**
- **PostgreSQL 15**
- **Flyway** (migrações)
- **Lombok** (redução de código)
- **SpringDoc OpenAPI** (Swagger)
- **Docker & Docker Compose**
