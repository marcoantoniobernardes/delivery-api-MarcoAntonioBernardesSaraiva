package com.delivery_api.Projeto.Delivery.API.service;

import java.util.List;

import com.delivery_api.Projeto.Delivery.API.dto.request.ClienteRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.ClienteResponseDTO;

public interface ClienteService {

    ClienteResponseDTO cadastrar(ClienteRequestDTO dto);

    ClienteResponseDTO buscarPorId(Long id);

    ClienteResponseDTO buscarPorEmail(String email);

    ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto);

    ClienteResponseDTO ativarDesativarCliente(Long id);

    List<ClienteResponseDTO> listarAtivos();

    List<ClienteResponseDTO> buscarPorNome(String nome);

}