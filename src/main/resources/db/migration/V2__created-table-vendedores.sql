CREATE TABLE IF NOT EXISTS vendedores (

    id VARCHAR(40) PRIMARY KEY DEFAULT (UUID()),
    nome_id VARCHAR(40),
    cpf VARCHAR(12) NOT NULL,
    cnpj VARCHAR(16),
    numero_telefone VARCHAR(11) NOT NULL,
    rua VARCHAR(100) NOT NULL,
    numero VARCHAR(10),
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    cep VARCHAR(15) NOT NULL,
    agencia VARCHAR(10) NOT NULL,
    conta VARCHAR(15) NOT NULL,
    codigo_banco VARCHAR(5) NOT NULL,

    FOREIGN KEY (nome_id) REFERENCES users(id) ON DELETE CASCADE
);