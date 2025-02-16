CREATE TABLE IF NOT EXISTS transacoes (

    id VARCHAR(40) PRIMARY KEY DEFAULT (UUID()),
    produto_id VARCHAR(40),
    comprador_id VARCHAR(40),
    quantidade INT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (produto_id) REFERENCES anuncios(id) ON DELETE CASCADE,

    FOREIGN KEY (comprador_id) REFERENCES compradores(id) ON DELETE CASCADE
);