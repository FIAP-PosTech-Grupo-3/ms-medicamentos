# MS Medicamentos - Documentação de Segurança

## 🔐 Autenticação Spring Security

### Configuração
- **Tipo**: Basic Authentication
- **Criptografia**: BCrypt (força 10)
- **Sessões**: Stateless (STATELESS)

### Credenciais de Teste

#### Usuário Administrador
- **Email**: `admin@medicamentos.com`
- **Senha**: `admin123`
- **Papel**: `ADMIN`
- **Permissões**: Acesso total a todas as operações

#### Usuário Padrão
- **Email**: `usuario@medicamentos.com`
- **Senha**: `admin123`
- **Papel**: `USUARIO`
- **Permissões**: Apenas consultas (GET)

### Regras de Autorização

#### Endpoints Públicos
- `/` - Página inicial
- `/swagger-ui/**` - Documentação Swagger
- `/v3/api-docs/**` - API Docs
- `/swagger-resources/**` - Recursos Swagger
- `/webjars/**` - WebJars

#### Consultas (GET) - Requer ADMIN ou USUARIO
- `GET /api/medicamentos/**`
- `GET /api/unidades-saude/**`
- `GET /api/estoque/**`

#### Consultas de Usuários (GET) - Requer ADMIN
- `GET /api/usuarios/**`

#### Operações de Criação/Atualização/Exclusão - Requer ADMIN
- `POST /api/medicamentos/**`
- `PUT /api/medicamentos/**`
- `DELETE /api/medicamentos/**`
- `POST /api/unidades-saude/**`
- `PUT /api/unidades-saude/**`
- `DELETE /api/unidades-saude/**`
- `POST /api/usuarios/**`
- `PUT /api/usuarios/**`
- `DELETE /api/usuarios/**`
- `POST /api/estoque/**`
- `PUT /api/estoque/**`

### Como Testar

#### 1. Via Swagger UI
1. Acesse: `http://localhost:8080/swagger-ui.html`
2. Clique em "Authorize"
3. Digite: `admin@medicamentos.com` / `admin123`

#### 2. Via cURL
```bash
# Como ADMIN
curl -X GET "http://localhost:8080/api/medicamentos" \
  -H "Authorization: Basic YWRtaW5AbWVkaWNhbWVudG9zLmNvbTphZG1pbjEyMw=="

# Como USUARIO
curl -X GET "http://localhost:8080/api/medicamentos" \
  -H "Authorization: Basic dXN1YXJpb0BtZWRpY2FtZW50b3MuY29tOmFkbWluMTIz"
```

#### 3. Via Postman
1. Selecione "Basic Auth"
2. Username: `admin@medicamentos.com`
3. Password: `admin123`

### Fluxo de Segurança

1. **Requisição**: Cliente envia credenciais via Basic Auth
2. **UserDetailsService**: Busca usuário por email no banco
3. **Validação**: Verifica senha criptografada com BCrypt
4. **Autorização**: Aplica regras baseadas no papel (ADMIN/USUARIO)
5. **Resposta**: Permite ou nega acesso ao endpoint

### Componentes Implementados

- `SecurityConfig`: Configuração principal de segurança
- `CustomUserDetailsService`: Integração com entidade Usuario
- `CadastrarUsuarioUseCase`: Criptografia de senhas no cadastro
- `PasswordEncoder`: Bean BCrypt para criptografia
