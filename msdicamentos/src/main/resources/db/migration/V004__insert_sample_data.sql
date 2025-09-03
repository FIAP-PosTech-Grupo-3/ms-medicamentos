-- Inserir algumas unidades de saúde de exemplo
INSERT INTO unidades_saude (nome, endereco, telefone, email, tipo, ativa) VALUES
('UBS Vila Madalena', 'Rua Fradique Coutinho, 123 - Vila Madalena', '(11) 3021-1234', 'ubs.vilamadalena@saude.sp.gov.br', 'UBS', true),
('Hospital das Clínicas', 'Av. Dr. Enéas de Carvalho Aguiar, 255 - Cerqueira César', '(11) 2661-0000', 'contato@hc.fm.usp.br', 'HOSPITAL', true),
('UBS Cidade Tiradentes', 'Rua dos Têxteis, 789 - Cidade Tiradentes', '(11) 2031-5678', 'ubs.cidadetiradentes@saude.sp.gov.br', 'UBS', true),
('Clínica São Paulo', 'Rua Augusta, 456 - Consolação', '(11) 3256-7890', 'contato@clinicasp.com.br', 'CLINICA', true);

-- Inserir alguns medicamentos de exemplo
INSERT INTO medicamentos (nome, tipo, fabricante, data_validade, dosagem, forma_farmaceutica, principio_ativo, lote, data_fabricacao, precisa_receita) VALUES
('Paracetamol', 'ANALGESICO', 'EMS', '2025-12-31', '500mg', 'COMPRIMIDO', 'Paracetamol', 'LOT001', '2024-01-15', false),
('Amoxicilina', 'ANTIBIOTICO', 'Medley', '2026-06-30', '875mg', 'COMPRIMIDO', 'Amoxicilina', 'LOT002', '2024-03-10', true),
('Dipirona', 'ANALGESICO', 'Neo Química', '2025-09-15', '500mg', 'COMPRIMIDO', 'Dipirona Sódica', 'LOT003', '2024-02-20', false),
('Omeprazol', 'GASTROPROTETOR', 'Eurofarma', '2026-01-31', '20mg', 'CAPSULA', 'Omeprazol', 'LOT004', '2024-04-05', false);

-- Associar medicamentos às unidades de saúde com quantidades
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
