CREATE TABLE "clientes" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(200),
    data_cadastro TIMESTAMP,
    ativo BOOLEAN
);

CREATE TABLE "restaurantes" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    endereco VARCHAR(200),
    cep VARCHAR(10),
    telefone VARCHAR(20),
    taxa_entrega DECIMAL(10,2),
    avaliacao DECIMAL(2,1),
    ativo BOOLEAN
);

CREATE TABLE "produtos" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(200),
    preco DECIMAL(10,2),
    categoria VARCHAR(50),
    disponivel BOOLEAN,
    restaurante_id INT,
    FOREIGN KEY (restaurante_id) REFERENCES "restaurantes"(id)
);

CREATE TABLE "pedidos" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_pedido VARCHAR(40) NOT NULL,
    data_pedido TIMESTAMP,
    status VARCHAR(20),
    valor_total DECIMAL(10,2),
    endereco_entrega VARCHAR(200),
    cep VARCHAR(10),
    taxa_entrega DECIMAL(10,2),
    itens_detalhes VARCHAR(200),
    observacoes VARCHAR(200),
    cliente_id INT,
    restaurante_id INT,
    itens VARCHAR(200),
    FOREIGN KEY (cliente_id) REFERENCES "clientes"(id),
    FOREIGN KEY (restaurante_id) REFERENCES "restaurantes"(id)
);
CREATE TABLE "usuarios" (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL, -- Deve ser grande o suficiente para o hash BCrypt
    role VARCHAR(20) NOT NULL,    -- Para armazenar o ENUM (ADMIN, CLIENTE, etc.)
    ativo BOOLEAN,
    data_criacao TIMESTAMP,

    restaurante_id BIGINT,

    FOREIGN KEY (restaurante_id) REFERENCES "restaurantes"(id)
);
CREATE TABLE "item_pedido" (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,

    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,

    FOREIGN KEY (pedido_id) REFERENCES "pedidos"(id),
    FOREIGN KEY (produto_id) REFERENCES "produtos"(id)
);
ALTER TABLE usuarios ALTER COLUMN id RESTART WITH 10;
