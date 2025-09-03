-- Criar tabela de unidades de saúde
CREATE TABLE unidades_saude (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco TEXT NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(255),
    tipo VARCHAR(50) NOT NULL,
    ativa BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela associativa medicamento_unidade_saude
CREATE TABLE medicamento_unidade_saude (
    id BIGSERIAL PRIMARY KEY,
    medicamento_id BIGINT NOT NULL,
    unidade_saude_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL DEFAULT 0,
    quantidade_minima INTEGER DEFAULT 10,
    ultima_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_medicamento_unidade_medicamento 
        FOREIGN KEY (medicamento_id) REFERENCES medicamentos(id) ON DELETE CASCADE,
    CONSTRAINT fk_medicamento_unidade_unidade_saude 
        FOREIGN KEY (unidade_saude_id) REFERENCES unidades_saude(id) ON DELETE CASCADE,
    CONSTRAINT uk_medicamento_unidade_saude 
        UNIQUE (medicamento_id, unidade_saude_id)
);

-- Remover colunas de estoque da tabela medicamentos
ALTER TABLE medicamentos 
DROP COLUMN IF EXISTS quantidade_estoque,
DROP COLUMN IF EXISTS em_falta;

-- Criar índices para performance
CREATE INDEX idx_medicamento_unidade_medicamento_id ON medicamento_unidade_saude(medicamento_id);
CREATE INDEX idx_medicamento_unidade_unidade_saude_id ON medicamento_unidade_saude(unidade_saude_id);
CREATE INDEX idx_medicamento_unidade_quantidade ON medicamento_unidade_saude(quantidade);
