package com.delivery_api.Projeto.Delivery.API.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.delivery_api.Projeto.Delivery.API.dto.request.ItemPedidoRequestDTO;
import com.delivery_api.Projeto.Delivery.API.model.Cliente;
import com.delivery_api.Projeto.Delivery.API.model.Restaurante;
import lombok.Data;

@Data
public class PedidoResponseDTO {
    private Long id;
    private String numeroPedido;
    private LocalDateTime dataPedido;
    private String status;
    private BigDecimal valorTotal;
    private String observacoes;
    private Cliente cliente;
    private Restaurante restaurante;
    private String enderecoEntrega;
    private BigDecimal taxaEntrega;
    List<ItemPedidoRequestDTO> itens;

}