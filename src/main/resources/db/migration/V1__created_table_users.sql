CREATE TABLE IF NOT EXISTS users (

    id VARCHAR(40) PRIMARY KEY DEFAULT (UUID()),
    primeiro_nome VARCHAR(40) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    tipo_user VARCHAR(10) NOT NULL,
    password VARCHAR(255) NOT NULL
);