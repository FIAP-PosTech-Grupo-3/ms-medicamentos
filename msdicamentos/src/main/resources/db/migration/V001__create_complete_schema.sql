-- =====================================================
-- MS MEDICAMENTOS - SCHEMA COMPLETO
-- =====================================================

-- Criar tabela de medicamentos
CREATE TABLE medicamentos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    principio_ativo VARCHAR(255),
    fabricante VARCHAR(255),
    dosagem VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de unidades de saúde
CREATE TABLE unidades_saude (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco TEXT NOT NULL,
    ativa BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de usuários
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    papel VARCHAR(20) NOT NULL CHECK (papel IN ('ADMIN', 'USUARIO')),
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela associativa medicamento_unidade_saude (N:N com quantidade)
CREATE TABLE medicamento_unidade_saude (
    id BIGSERIAL PRIMARY KEY,
    medicamento_id BIGINT NOT NULL,
    unidade_saude_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL DEFAULT 0,
    quantidade_minima INTEGER DEFAULT 10,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_medicamento_unidade_medicamento 
        FOREIGN KEY (medicamento_id) REFERENCES medicamentos(id) ON DELETE CASCADE,
    CONSTRAINT fk_medicamento_unidade_unidade_saude 
        FOREIGN KEY (unidade_saude_id) REFERENCES unidades_saude(id) ON DELETE CASCADE,
    CONSTRAINT uk_medicamento_unidade_saude 
        UNIQUE (medicamento_id, unidade_saude_id)
);

-- =====================================================
-- ÍNDICES PARA PERFORMANCE
-- =====================================================

-- Índices para medicamentos
CREATE INDEX idx_medicamentos_nome ON medicamentos(nome);

-- Índices para unidades de saúde
CREATE INDEX idx_unidades_saude_nome ON unidades_saude(nome);

-- Índices para usuários
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_ativo ON usuarios(ativo);
CREATE INDEX idx_usuarios_papel ON usuarios(papel);

-- Índices para relacionamento medicamento-unidade
CREATE INDEX idx_medicamento_unidade_medicamento_id ON medicamento_unidade_saude(medicamento_id);
CREATE INDEX idx_medicamento_unidade_unidade_saude_id ON medicamento_unidade_saude(unidade_saude_id);
CREATE INDEX idx_medicamento_unidade_quantidade ON medicamento_unidade_saude(quantidade);

-- =====================================================
-- DADOS INICIAIS
-- =====================================================

-- Inserir usuários do sistema
INSERT INTO usuarios (nome, email, senha, papel, ativo, data_criacao) VALUES
('Administrador', 'admin@medicamentos.com', '$2a$10$Nh7wnCOV4I2eKGMzkGOJMOfwXjSCKlJ8WJLo.qY9QRj8BQJH1y/9W', 'ADMIN', true, CURRENT_TIMESTAMP),
('Usuario Teste', 'usuario@medicamentos.com', '$2a$10$Nh7wnCOV4I2eKGMzkGOJMOfwXjSCKlJ8WJLo.qY9QRj8BQJH1y/9W', 'USUARIO', true, CURRENT_TIMESTAMP);
-- Senha padrão para ambos: admin123 (hash BCrypt)

-- Inserir unidades de saúde
INSERT INTO unidades_saude (nome, endereco, ativa) VALUES
('UBS Vila Madalena', 'Rua Fradique Coutinho, 123 - Vila Madalena', true),
('Hospital das Clínicas', 'Av. Dr. Enéas de Carvalho Aguiar, 255 - Cerqueira César', true),
('UBS Cidade Tiradentes', 'Rua dos Têxteis, 789 - Cidade Tiradentes', true),
('Clínica São Paulo', 'Rua Augusta, 456 - Consolação', true);

-- Inserir medicamentos
INSERT INTO medicamentos (nome, principio_ativo, fabricante, dosagem) VALUES
('Paracetamol', 'Paracetamol', 'EMS', '500mg'),
('Amoxicilina', 'Amoxicilina', 'Medley', '875mg'),
('Dipirona', 'Dipirona Sódica', 'Neo Química', '500mg'),
('Omeprazol', 'Omeprazol', 'Eurofarma', '20mg');

-- Inserir associações medicamento-unidade com quantidades
-- UBS Vila Madalena
INSERT INTO medicamento_unidade_saude (medicamento_id, unidade_saude_id, quantidade, quantidade_minima) VALUES
(1, 1, 150, 20), -- Paracetamol
(3, 1, 80, 15),  -- Dipirona
(4, 1, 60, 10);  -- Omeprazol

-- Hospital das Clínicas
INSERT INTO medicamento_unidade_saude (medicamento_id, unidade_saude_id, quantidade, quantidade_minima) VALUES
(1, 2, 500, 50), -- Paracetamol
(2, 2, 300, 30), -- Amoxicilina
(3, 2, 400, 40), -- Dipirona
(4, 2, 200, 25); -- Omeprazol

-- UBS Cidade Tiradentes
INSERT INTO medicamento_unidade_saude (medicamento_id, unidade_saude_id, quantidade, quantidade_minima) VALUES
(1, 3, 100, 20), -- Paracetamol
(3, 3, 50, 15),  -- Dipirona
(4, 3, 30, 10);  -- Omeprazol

-- Clínica São Paulo
INSERT INTO medicamento_unidade_saude (medicamento_id, unidade_saude_id, quantidade, quantidade_minima) VALUES
(1, 4, 75, 15),  -- Paracetamol
(2, 4, 40, 10),  -- Amoxicilina
(4, 4, 25, 8);   -- Omeprazol
