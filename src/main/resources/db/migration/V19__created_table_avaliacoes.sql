CREATE TABLE IF NOT EXISTS avaliacoes (

    id VARCHAR(40) PRIMARY KEY DEFAULT (UUID()),
    produto_id VARCHAR(40) NOT NULL,
    comprador_id VARCHAR(40) NOT NULL,
    nota DECIMAL(3,1) NOT NULL CHECK (nota BETWEEN 0 AND 5),
    comentario TEXT,

    FOREIGN KEY (produto_id) REFERENCES anuncios(id) ON DELETE CASCADE,

    FOREIGN KEY (comprador_id) REFERENCES compradores(id) ON DELETE CASCADE

);