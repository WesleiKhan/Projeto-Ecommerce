CREATE TABLE IF NOT EXISTS favoritos (

    id VARCHAR(40) PRIMARY KEY DEFAULT (UUID()),
    user_id VARCHAR(40),
    produto_id VARCHAR(40),

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,

    FOREIGN KEY (produto_id) REFERENCES anuncios(id) ON DELETE CASCADE
);