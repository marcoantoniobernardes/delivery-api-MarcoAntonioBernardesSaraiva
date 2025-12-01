-- Inserir clientes
INSERT INTO clientes (nome, email, telefone, endereco, data_cadastro, ativo) VALUES
('João Silva', 'joao@email.com', '(11) 99999-1111', 'Rua A, 123 - São Paulo/SP', CURRENT_TIMESTAMP, true),
('Maria Santos', 'maria@email.com', '(11) 99999-2222', 'Rua B, 456 - São Paulo/SP', CURRENT_TIMESTAMP, true),
('Pedro Oliveira', 'pedro@email.com', '(11) 99999-3333', 'Rua C, 789 - São Paulo/SP', CURRENT_TIMESTAMP, true);

-- Inserir restaurantes
INSERT INTO restaurantes (nome, categoria, endereco, cep, telefone, taxa_entrega, avaliacao, ativo) VALUES
('Pizzaria Bella', 'Italiana', 'Av. Paulista, 1000 - São Paulo/SP', '01311-200', '(11) 3333-1111', 5.00, 4.5, true),
('Burger House', 'Hamburgueria', 'Rua Augusta, 500 - São Paulo/SP', '01305-000', '(11) 3333-2222', 3.50, 4.2, true),
('Sushi Master', 'Japonesa', 'Rua Liberdade, 200 - São Paulo/SP', '01503-001', '(11) 3333-3333', 8.00, 4.8, true);

-- Inserir produtos
INSERT INTO produtos (nome, descricao, preco, categoria, disponivel, restaurante_id) VALUES
-- Pizzaria Bella
('Pizza Margherita', 'Molho de tomate, mussarela e manjericão', 35.90, 'Pizza', true, 1),
('Pizza Calabresa', 'Molho de tomate, mussarela e calabresa', 38.90, 'Pizza', true, 1),
('Lasanha Bolonhesa', 'Lasanha tradicional com molho bolonhesa', 28.90, 'Massa', true, 1),

-- Burger House
('X-Burger', 'Hambúrguer, queijo, alface e tomate', 18.90, 'Hambúrguer', true, 2),
('X-Bacon', 'Hambúrguer, queijo, bacon, alface e tomate', 22.90, 'Hambúrguer', true, 2),
('Batata Frita', 'Porção de batata frita crocante', 12.90, 'Acompanhamento', true, 2),

-- Sushi Master
('Combo Sashimi', '15 peças de sashimi variado', 45.90, 'Sashimi', true, 3),
('Hot Roll Salmão', '8 peças de hot roll de salmão', 32.90, 'Hot Roll', true, 3),
('Temaki Atum', 'Temaki de atum com cream cheese', 15.90, 'Temaki', true, 3);

-- Inserir pedidos de exemplo
INSERT INTO pedidos (
    numero_pedido,
    data_pedido,
    status,
    valor_total,
    observacoes,
    endereco_entrega,
    cep,
    taxa_entrega,
    cliente_id,
    restaurante_id,
    itens
) VALUES
-- Pedido 1: Cliente 1 (Rua A) / Restaurante 1 (Taxa 5.00)
('PED1234567890', CURRENT_TIMESTAMP, 'PENDENTE', 54.80, 'Sem cebola na pizza', 'Rua A, 123 - São Paulo/SP', '01000-001', 5.00, 1, 1, 'Pizza Margherita, Pizza Calabresa'),

-- Pedido 2: Cliente 2 (Rua B) / Restaurante 2 (Taxa 3.50)
('PED1234567891', CURRENT_TIMESTAMP, 'CONFIRMADO', 41.80, '', 'Rua B, 456 - São Paulo/SP', '01000-002', 3.50, 2, 2, 'X-Burger, Batata Frita'),

-- Pedido 3: Cliente 3 (Rua C) / Restaurante 3 (Taxa 8.00)
('PED1234567892', CURRENT_TIMESTAMP, 'ENTREGUE', 78.80, 'Wasabi à parte', 'Rua C, 789 - São Paulo/SP', '01000-003', 8.00, 3, 3, 'Combo Sashimi, Hot Roll Salmão, Temaki Atum');

--inserir itens pedido
INSERT INTO item_pedido (quantidade, preco_unitario, subtotal, pedido_id, produto_id) VALUES
(1, 35.90, 35.90, 1, 1), -- Pizza Margherita no pedido 1
(1, 18.90, 18.90, 1, 2), -- Pizza Calabresa no pedido 1

(1, 18.90, 18.90, 2, 4), -- X-Burger no pedido 2
(1, 12.90, 12.90, 2, 6), -- Batata Frita no pedido 2

(1, 45.90, 45.90, 3, 7), -- Combo Sashimi no pedido 3
(1, 32.90, 32.90, 3, 8), -- Hot Roll Salmão no pedido 3
(1, 15.90, 15.90, 3, 9); -- Temaki Atum no pedido 3
-- Inserir usuários
INSERT INTO usuarios (id, nome, email, senha, role, ativo, data_criacao , restaurante_id) VALUES
(1, 'Admin Sistema', 'admin@delivery.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'ADMIN', 1, '2025-07-31 00:00:00', NULL),
(2, 'João Cliente', 'joao@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'CLIENTE', 1, '2025-07-31 00:00:00', NULL),
(3, 'Maria Cliente', 'maria@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'CLIENTE', 1, '2025-07-31 00:00:00', NULL),
(4, 'Pizza Palace', 'pizza@palace.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'RESTAURANTE', 1, '2025-07-31 00:00:00', 1),
(5, 'Burger King', 'burger@king.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'RESTAURANTE', 1, '2025-07-31 00:00:00', 2);