package com.delivery_api.Projeto.Delivery.API.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.delivery_api.Projeto.Delivery.API.dto.ItemPedidoRequestDTO; // Import necessário
import com.delivery_api.Projeto.Delivery.API.dto.PedidoRequestDTO;
import com.delivery_api.Projeto.Delivery.API.model.Produto; // Import necessário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery_api.Projeto.Delivery.API.model.Cliente;
import com.delivery_api.Projeto.Delivery.API.model.Pedido;
import com.delivery_api.Projeto.Delivery.API.model.Restaurante;
import com.delivery_api.Projeto.Delivery.API.enums.StatusPedido;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import com.delivery_api.Projeto.Delivery.API.repository.PedidoRepository;
import com.delivery_api.Projeto.Delivery.API.repository.ProdutoRepository;
import com.delivery_api.Projeto.Delivery.API.repository.RestauranteRepository;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Criar novo pedido
     */
    public Pedido criarPedido(PedidoRequestDTO dto) {
        // 1. Validação de Cliente e Restaurante
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + dto.getClienteId()));

        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + dto.getRestauranteId()));

        if (!cliente.getAtivo()) {
            throw new IllegalArgumentException("Cliente inativo não pode fazer pedidos");
        }

        if (!restaurante.getAtivo()) {
            throw new IllegalArgumentException("Restaurante não está disponível");
        }

        // 2. Criação e Mapeamento
        Pedido pedido = new Pedido();
        pedido.setClienteId(cliente.getId());
        pedido.setRestaurante(restaurante); // Presumindo que o Pedido tem um campo Restaurante

        // 3. Geração de Dados de Auditoria e Status (CORREÇÃO DE COMPILAÇÃO)
        pedido.setStatus(StatusPedido.PENDENTE.name());
        pedido.setDataPedido(LocalDateTime.now()); // Gerado no Service
        pedido.setNumeroPedido("PED" + System.currentTimeMillis()); // Gerado no Service

        // 4. Cálculo de Valor Total (CORREÇÃO DE LÓGICA)
        BigDecimal valorProdutos = calcularValorTotal(dto.getItens());
        BigDecimal valorTotal = valorProdutos.add(restaurante.getTaxaEntrega());

        pedido.setValorTotal(valorTotal);
        pedido.setObservacoes(dto.getObservacoes());

        // 5. Mapeamento dos Itens (Conversão para String)
        // Note: Isso armazena os itens como string. Idealmente, usaria uma tabela ItemPedido.
        pedido.setItens(dto.getItens().toString());

        return pedidoRepository.save(pedido);
    }

    // --- MÉTODO AUXILIAR PARA CÁLCULO DO VALOR TOTAL ---
    private BigDecimal calcularValorTotal(List<ItemPedidoRequestDTO> itensDTO) {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO item : itensDTO) {
            Optional<Produto> produtoOpt = produtoRepository.findById(item.getProdutoId());

            if (produtoOpt.isEmpty()) {
                throw new IllegalArgumentException("Produto não encontrado: " + item.getProdutoId());
            }

            Produto produto = produtoOpt.get();

            // Validação de produto disponível (importante!)
            if (!produto.getDisponivel()) {
                throw new IllegalArgumentException("Produto indisponível: " + produto.getNome());
            }

            BigDecimal precoUnitario = produto.getPreco();
            BigDecimal quantidade = new BigDecimal(item.getQuantidade());

            BigDecimal subtotal = precoUnitario.multiply(quantidade);
            total = total.add(subtotal);
        }

        return total;
    }

    // --- MÉTODOS EXISTENTES ABAIXO ---

    /**
     * Listar pedidos por cliente
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteIdOrderByDataPedidoDesc(clienteId);
    }

    /**
     * Atualizar status do pedido
     */
    public Pedido atualizarStatus(Long pedidoId, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

        if (pedido.getStatus().equals(StatusPedido.ENTREGUE.name())) {
            throw new IllegalArgumentException("Pedido já finalizado: " + pedidoId);
        }

        pedido.setStatus(status.name());
        return pedidoRepository.save(pedido);
    }
    // Pedidos por cliente
    public List<Pedido> buscarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    // listar por status
    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }
    // Listar os 10 pedidos mais recentes
    public List<Pedido> listarRecentes() {
        return pedidoRepository.findTop10ByOrderByDataPedidoDesc();
    }
    /**
     * Listar pedidos por período
     */
    public List<Pedido> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return pedidoRepository.findByDataPedidoBetween(inicio, fim);
    }
}