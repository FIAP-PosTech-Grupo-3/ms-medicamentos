    
CREATE TABLE medicamentos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    principio_ativo VARCHAR(255),
    fabricante VARCHAR(255),
    dosagem VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE unidades_saude (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco TEXT NOT NULL,
    ativa BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    papel VARCHAR(20) NOT NULL CHECK (papel IN ('ADMIN', 'USUARIO')),
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE medicamento_unidade_saude (
    id BIGSERIAL PRIMARY KEY,
    medicamento_id BIGINT NOT NULL,
    unidade_saude_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL DEFAULT 0,
    quantidade_minima INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (medicamento_id) REFERENCES medicamentos(id) ON DELETE CASCADE,
    FOREIGN KEY (unidade_saude_id) REFERENCES unidades_saude(id) ON DELETE CASCADE,
    UNIQUE(medicamento_id, unidade_saude_id)
);

CREATE INDEX idx_medicamentos_nome ON medicamentos(nome);
CREATE INDEX idx_unidades_saude_nome ON unidades_saude(nome);
CREATE INDEX idx_unidades_saude_ativa ON unidades_saude(ativa);
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_ativo ON usuarios(ativo);
CREATE INDEX idx_usuarios_papel ON usuarios(papel);
CREATE INDEX idx_medicamento_unidade_medicamento_id ON medicamento_unidade_saude(medicamento_id);
CREATE INDEX idx_medicamento_unidade_unidade_saude_id ON medicamento_unidade_saude(unidade_saude_id);
CREATE INDEX idx_medicamento_unidade_quantidade ON medicamento_unidade_saude(quantidade);

INSERT INTO unidades_saude (nome, endereco, ativa, created_at, updated_at) VALUES
('UBS Vila Nova', 'Rua das Flores, 123 - Vila Nova, São Paulo - SP', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Hospital Central', 'Av. Principal, 456 - Centro, São Paulo - SP', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pronto Socorro Norte', 'Rua do Socorro, 789 - Zona Norte, São Paulo - SP', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO medicamentos (nome, principio_ativo, fabricante, dosagem, created_at, updated_at) VALUES
('Paracetamol 500mg', 'Paracetamol', 'EMS', '500mg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Dipirona 500mg', 'Metamizol Sódico', 'Medley', '500mg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Omeprazol 20mg', 'Omeprazol', 'EMS', '20mg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Losartana 50mg', 'Losartana Potássica', 'EMS', '50mg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Metformina 850mg', 'Cloridrato de Metformina', 'Medley', '850mg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO medicamento_unidade_saude (medicamento_id, unidade_saude_id, quantidade, quantidade_minima, created_at, updated_at) VALUES
(1, 1, 500, 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 300, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 100, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1000, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 800, 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 200, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 150, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 250, 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 750, 75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 600, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 100, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO usuarios (nome, email, senha, papel, ativo, data_criacao) VALUES
('Administrador', 'admin', 'admin', 'ADMIN', true, CURRENT_TIMESTAMP),
('Usuario', 'user', 'user', 'USUARIO', true, CURRENT_TIMESTAMP);
