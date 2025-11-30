package com.delivery_api.Projeto.Delivery.API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {

    // Campos que o USUÁRIO ENVIA na requisição POST:
    private Long clienteId;
    private Long restauranteId;
    private String observacoes; // Opcional

    private List<ItemPedidoRequestDTO> itens;

}