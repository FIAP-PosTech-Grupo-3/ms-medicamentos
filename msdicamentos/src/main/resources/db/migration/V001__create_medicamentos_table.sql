CREATE TABLE medicamentos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    fabricante VARCHAR(255),
    data_validade DATE,
    dosagem VARCHAR(100),
    forma_farmaceutica VARCHAR(50),
    principio_ativo VARCHAR(255),
    lote VARCHAR(100),
    data_fabricacao DATE,
    quantidade_estoque INTEGER DEFAULT 0,
    precisa_receita BOOLEAN DEFAULT FALSE,
    em_falta BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
