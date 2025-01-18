CREATE TABLE IF NOT EXISTS anuncios (

    id VARCHAR(40) PRIMARY KEY DEFAULT (UUID()),
    titulo VARCHAR(150) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    imagem VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    vendedor_id VARCHAR(40),
    altura DOUBLE NOT NULL,
    largura DOUBLE NOT NULL,
    comprimento DOUBLE NOT NULL,
    peso DOUBLE NOT NULL,

    FOREIGN KEY (vendedor_id) REFERENCES vendedores(id) ON DELETE CASCADE
);