# MS Medicamentos - Sistema de Gerenciamento

Este projeto é uma API REST para gerenciamento de medicamentos, desenvolvida com **Spring Boot 3** e totalmente containerizada com **Docker**.

---

## 🚀 Execução Rápida

```bash
# Clone o repositório
git clone https://github.com/FIAP-PosTech-Grupo-3/ms-medicamentos.git

# Execute com Docker Compose
docker-compose up --build
```

---

## 📋 Pré-requisitos

- Docker instalado
- Docker Compose instalado

---

## 🛠️ Recursos Incluídos

- **PostgreSQL** (banco de dados)
- **Flyway** (migrações automáticas)
- **Spring Boot 3** (aplicação)
- **Build automático** (Maven integrado)
- **Swagger UI** (documentação)

---

## 🔑 Autenticação

A API utiliza **Basic Authentication**. Usuários são criados automaticamente na primeira execução:

### Credenciais Padrão

| Email                   | Senha     | Papel   | Descrição                           |
|-------------------------|-----------|---------|-------------------------------------|
| `admin@admin.com`       | `admin`   | ADMIN   | Administrador com acesso total      |
| `user@user.com`         | `user`    | USUARIO | Usuário comum (apenas consultas)    |
| `admin@medicamentos.com`| `admin123`| ADMIN   | Admin alternativo                   |

### Permissões

- **ADMIN** → CRUD completo em todos os endpoints  
- **USUARIO** → Apenas consultas (GET) em medicamentos, unidades e estoque  

---

## 📡 Exemplos de Requisições (REST Client / Postman)

Você pode testar a API usando o **REST Client** do VS Code (arquivo `requests.http`) ou importando no **Postman**.

### Variáveis globais

```http
@baseUrl = http://localhost:8080
@contentType = application/json
@adminAuth = Basic admin admin
@userAuth = Basic user user
```

---

### 👤 Usuários

#### 1. Criar usuário comum
```http
POST {{baseUrl}}/api/usuarios
Content-Type: {{contentType}}

{
  "nome": "Novo Usuário Teste",
  "email": "novo.usuario@teste.com",
  "senha": "senha123",
  "papel": "USUARIO"
}
```

#### 2. Criar usuário ADMIN
```http
POST {{baseUrl}}/api/usuarios
Content-Type: {{contentType}}

{
  "nome": "Novo Admin Teste",
  "email": "novo.admin@teste.com",
  "senha": "admin123",
  "papel": "ADMIN"
}
```

#### 3. Criar usuário com papel inválido
```http
POST {{baseUrl}}/api/usuarios
Content-Type: {{contentType}}

{
  "nome": "Usuário com Erro",
  "email": "erro@teste.com",
  "senha": "senha123",
  "papel": "INVALIDO"
}
```

#### 4. Listar usuários (ADMIN)
```http
GET {{baseUrl}}/api/usuarios
Authorization: {{adminAuth}}
Accept: {{contentType}}
```

#### 5. Listar usuários (USUARIO)
```http
GET {{baseUrl}}/api/usuarios
Authorization: {{userAuth}}
Accept: {{contentType}}
```

#### 6. Listar usuários sem autenticação
```http
GET {{baseUrl}}/api/usuarios
Accept: {{contentType}}
```

#### 7. Buscar usuário por ID
```http
GET {{baseUrl}}/api/usuarios/1
Authorization: {{adminAuth}}
Accept: {{contentType}}
```

#### 8. Atualizar usuário (ADMIN)
```http
PUT {{baseUrl}}/api/usuarios/1
Authorization: {{adminAuth}}
Content-Type: {{contentType}}

{
  "nome": "Administrador Atualizado",
  "email": "admin",
  "papel": "ADMIN",
  "ativo": true
}
```

#### 9. Atualizar usuário sem permissão (USUARIO)
```http
PUT {{baseUrl}}/api/usuarios/2
Authorization: {{userAuth}}
Content-Type: {{contentType}}

{
  "nome": "Usuário Comum Tentando Atualizar",
  "email": "user",
  "papel": "USUARIO",
  "ativo": false
}
```

#### 10. Deletar usuário (ADMIN)
```http
DELETE {{baseUrl}}/api/usuarios/3
Authorization: {{adminAuth}}
Accept: {{contentType}}
```

---

### 💊 Medicamentos

#### 11. Listar medicamentos (Público)
```http
GET {{baseUrl}}/api/medicamentos
Accept: {{contentType}}
```

#### 12. Criar medicamento (ADMIN)
```http
POST {{baseUrl}}/api/medicamentos
Authorization: {{adminAuth}}
Content-Type: {{contentType}}

{
  "nome": "Ibuprofeno 600mg",
  "dosagem": "600mg",
  "fabricante": "Medley",
  "principioAtivo": "Ibuprofeno"
}
```

#### 13. Criar medicamento sem permissão (USUARIO)
```http
POST {{baseUrl}}/api/medicamentos
Authorization: {{userAuth}}
Content-Type: {{contentType}}

{
  "nome": "Aspirina (sem permissão)",
  "dosagem": "500mg",
  "fabricante": "Bayer",
  "principioAtivo": "Ácido acetilsalicílico"
}
```

---

### 🏥 Unidades de Saúde

#### 14. Listar unidades de saúde
```http
GET {{baseUrl}}/api/unidades-saude
Accept: {{contentType}}
```

#### 15. Criar unidade de saúde (ADMIN)
```http
POST {{baseUrl}}/api/unidades-saude
Authorization: {{adminAuth}}
Content-Type: {{contentType}}

{
  "nome": "UBS Central",
  "endereco": "Rua Principal, 100",
  "ativa": true
}
```

---

### 📦 Estoque

#### 16. Adicionar estoque (ADMIN)
```http
POST {{baseUrl}}/api/estoque/adicionar
Authorization: {{adminAuth}}
Content-Type: {{contentType}}

{
  "medicamentoId": 1,
  "unidadeSaudeId": 1,
  "quantidade": 100,
  "quantidadeMinima": 20
}
```

#### 17. Remover estoque (ADMIN)
```http
POST {{baseUrl}}/api/estoque/remover
Authorization: {{adminAuth}}
Content-Type: {{contentType}}

{
  "medicamentoId": 1,
  "unidadeSaudeId": 1,
  "quantidade": 10
}
```

#### 18. Consultar estoque
```http
GET {{baseUrl}}/api/estoque
Accept: {{contentType}}
```

#### 19. Buscar estoque por medicamento e unidade
```http
GET {{baseUrl}}/api/estoque/medicamento/1/unidade/1
Accept: {{contentType}}
```

#### 20. Buscar estoque não existente
```http
GET {{baseUrl}}/api/estoque/medicamento/999/unidade/999
Accept: {{contentType}}
```

---

## 📖 Swagger UI

Acesse a documentação interativa em:
- **Swagger UI** → http://localhost:8080/swagger-ui  
- **API Docs JSON** → http://localhost:8080/api-docs  

---

## 🗄️ Banco de Dados

O sistema utiliza um modelo relacional com 3 tabelas principais:

- **medicamentos** → Dados básicos dos medicamentos  
- **unidades_saude** → Cadastro de unidades de saúde (UBS, hospitais, clínicas)  
- **medicamento_unidade_saude** → Relaciona medicamentos com unidades e controla estoque  

⚡ Dados de exemplo são inseridos automaticamente na primeira execução.

---

## 📝 Logs & Tratamento de Erros

### Logs
- **Console** → Logs básicos  
- **Arquivo** → `logs/application.log` (rotacionado diariamente)

### Códigos de Erro
- **400** → Requisição inválida  
- **403** → Sem permissão  
- **404** → Não encontrado  
- **422** → Erro de regra de negócio  
- **500** → Erro interno do servidor  

---

## 💻 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.5**
- **PostgreSQL 15**
- **Flyway**
- **Lombok**
- **SpringDoc OpenAPI**
- **Docker & Docker Compose**
