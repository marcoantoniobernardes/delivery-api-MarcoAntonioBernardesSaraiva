package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.dto.request.LoginRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.request.UsuarioRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.LoginResponseDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.UsuarioResponseDTO;
import com.delivery_api.Projeto.Delivery.API.exception.ModelNotFoundException;
import com.delivery_api.Projeto.Delivery.API.model.Usuario;
import com.delivery_api.Projeto.Delivery.API.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioService {

    UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto);

    LoginResponseDTO login(LoginRequestDTO dto);

    UsuarioResponseDTO getAuthenticatedUser();

}