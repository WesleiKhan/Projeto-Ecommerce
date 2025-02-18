ALTER TABLE saques 
ADD COLUMN transacoes_id VARCHAR(40),
ADD CONSTRAINT fk_transacoes FOREIGN KEY (transacoes_id) REFERENCES transacoes(id) ON DELETE CASCADE;