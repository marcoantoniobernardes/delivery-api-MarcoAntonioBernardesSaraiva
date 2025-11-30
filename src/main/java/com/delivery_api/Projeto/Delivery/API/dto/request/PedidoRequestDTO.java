package com.delivery_api.Projeto.Delivery.API.dto.request;

import com.delivery_api.Projeto.Delivery.API.validation.ValidCEP;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {

    // CAMPOS QUE DEVEM SER GERADOS PELO SERVIDOR - ANOTAÇÕES REMOVIDAS.

    @Schema(description = "Observações do pedido", example = "Não colocar cebola")
    private String observacoes;

    // CAMPOS DE ENTRADA ESSENCIAIS (VALIAÇÕES MANTIDAS)

    @Schema(description = "ID do cliente", example = "1", required = true)
    @NotNull(message = "O ID do cliente é obrigatório") // OK
    private Long clienteId;

    @Schema(description = "ID do restaurante", example = "1", required = true)
    @NotNull(message = "O restaurante é obrigatório") // OK
    private Long restauranteId;

    @Schema(description = "Endereço de entrega do pedido", example = "Rua das Flores, 123")
    private String enderecoEntrega;

    @NotBlank(message = "CEP é obrigatório") // OK
    @ValidCEP
    private String cep;

    @Schema(description = "Lista de itens do pedido", required = true)
    @NotEmpty(message = "Os itens são obrigatórios") // OK
    @Valid
    private List<ItemPedidoRequestDTO> itens;
}