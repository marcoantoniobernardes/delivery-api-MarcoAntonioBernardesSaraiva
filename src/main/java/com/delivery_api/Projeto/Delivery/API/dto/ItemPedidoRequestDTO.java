package com.delivery_api.Projeto.Delivery.API.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequestDTO {

    // Mapeia o campo "produtoId" do JSON
    private Long produtoId;

    // Mapeia o campo "quantidade" do JSON
    private Integer quantidade;
}