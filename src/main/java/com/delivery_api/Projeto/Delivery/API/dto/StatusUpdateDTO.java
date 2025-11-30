package com.delivery_api.Projeto.Delivery.API.dto;

import com.delivery_api.Projeto.Delivery.API.enums.StatusPedido;
import lombok.Data;

@Data
public class StatusUpdateDTO {
    private StatusPedido status;
}