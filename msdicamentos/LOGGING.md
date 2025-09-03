# 📋 Sistema de Logging - MS Medicamentos

## 🎯 Configuração Implementada

O sistema de logging foi configurado usando **Logback** com formatação personalizada e múltiplos appenders.

## 📁 Estrutura de Logs

### 📄 **Arquivos de Log Gerados:**

```
logs/
├── ms-medicamentos.log          # Log geral da aplicação
├── ms-medicamentos-error.log    # Apenas logs de erro (ERROR/FATAL)
├── ms-medicamentos.2025-09-03.0.log  # Arquivo rotacionado (diário)
└── ms-medicamentos-error.2025-09-03.0.log # Erros rotacionados
```

### 🔄 **Rotação de Logs:**
- **Tamanho máximo**: 10MB por arquivo
- **Histórico**: 30 dias
- **Tamanho total**: 300MB (geral) / 100MB (erros)
- **Formato**: `arquivo.YYYY-MM-DD.{índice}.log`

## 🎨 **Formatação dos Logs**

### **Console (Colorido):**
```
2025-09-03 14:30:15.123  INFO 12345 --- [main] f.m.adapter.web.MedicamentoController : Medicamento cadastrado com sucesso - ID: 1
```

### **Arquivo (Simples):**
```
2025-09-03 14:30:15.123 INFO 12345 --- [main] f.m.adapter.web.MedicamentoController : Medicamento cadastrado com sucesso - ID: 1
```

## 📊 **Níveis de Log por Componente**

| Componente | Nível | Descrição |
|------------|-------|-----------|
| `fiap.msmedicamentos` | **DEBUG** | Logs detalhados da aplicação |
| `org.springframework` | **INFO** | Framework Spring |
| `org.hibernate.SQL` | **DEBUG** | Consultas SQL executadas |
| `org.hibernate.type` | **TRACE** | Parâmetros SQL (bind values) |
| `org.flywaydb` | **INFO** | Migrações de banco |
| `ROOT` | **INFO** | Outros componentes |

## 🏗️ **Profiles de Ambiente**

### **Development (dev):**
- **Console**: DEBUG
- **Arquivo**: Opcional
- **SQL**: Visível

### **Production (prod):**
- **Console**: Desabilitado
- **Arquivo**: WARN+
- **SQL**: Oculto

## 📝 **Exemplos de Logs na Aplicação**

### **🚀 Inicialização:**
```
========================================
🏥 Iniciando MS Medicamentos...
========================================
✅ MS Medicamentos iniciado com sucesso!
📊 Swagger UI: http://localhost:8080/swagger-ui
========================================
```

### **📥 Request/Response:**
```
INFO  --- [http-nio-8080-exec-1] f.m.adapter.web.MedicamentoController : Iniciando cadastro de medicamento: Paracetamol
DEBUG --- [http-nio-8080-exec-1] f.m.adapter.web.MedicamentoController : Dados do medicamento: CadastrarMedicamentoRequest(nome=Paracetamol, tipo=ANALGESICO...)
INFO  --- [http-nio-8080-exec-1] f.m.core.usecase.CadastrarMedicamentoUseCase : Medicamento cadastrado com sucesso no domínio - ID: 1
```

### **⚠️ Validação:**
```
WARN  --- [http-nio-8080-exec-2] f.m.core.usecase.CadastrarMedicamentoUseCase : Tentativa de cadastro com nome inválido
WARN  --- [http-nio-8080-exec-2] f.m.adapter.web.exception.GlobalExceptionHandler : Erro de validação de medicamento: nome - URI: /api/medicamentos
```

### **🔍 SQL (Debug):**
```
DEBUG --- [http-nio-8080-exec-1] org.hibernate.SQL : insert into medicamentos (nome, tipo, fabricante...) values (?, ?, ?...)
TRACE --- [http-nio-8080-exec-1] o.h.type.descriptor.sql.BasicBinder : binding parameter [1] as [VARCHAR] - [Paracetamol]
```

## ⚙️ **Configurações Customizáveis**

### **application.properties:**
```properties
# Alterar nível de log da aplicação
logging.level.fiap.msmedicamentos=INFO

# Desabilitar SQL logs
logging.level.org.hibernate.SQL=WARN

# Profile ativo
spring.profiles.active=prod
```

### **Variáveis de Ambiente:**
```bash
# Ativar profile de produção
export SPRING_PROFILES_ACTIVE=prod

# Customizar nível específico
export LOGGING_LEVEL_FIAP_MSMEDICAMENTOS=WARN
```

## 🔧 **Monitoramento e Alertas**

### **Logs de Erro Crítico:**
- Salvos em arquivo separado: `ms-medicamentos-error.log`
- Incluem stack trace completo
- Podem ser integrados com sistemas de monitoramento

### **Métricas Sugeridas:**
- Contagem de erros por minuto
- Tempo de resposta das APIs
- Falhas de validação
- Exceções não tratadas

## 📋 **Boas Práticas Implementadas**

✅ **Logs estruturados** com informações consistentes  
✅ **Separação por nível** (DEBUG, INFO, WARN, ERROR)  
✅ **Rotação automática** para evitar arquivos grandes  
✅ **Formatação colorida** no console para desenvolvimento  
✅ **Logs de auditoria** para operações importantes  
✅ **Stack trace** completo em erros críticos  
✅ **Configuração por ambiente** (dev/prod)  

## 🎯 **Localização dos Arquivos**

- **Configuração**: `src/main/resources/logback-spring.xml`
- **Properties**: `src/main/resources/application.properties`
- **Logs gerados**: `logs/` (criado automaticamente)
- **Git ignore**: Logs são ignorados no controle de versão
