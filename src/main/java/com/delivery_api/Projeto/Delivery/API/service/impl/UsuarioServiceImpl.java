package com.delivery_api.Projeto.Delivery.API.service.impl;

import com.delivery_api.Projeto.Delivery.API.dto.request.LoginRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.request.UsuarioRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.LoginResponseDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.UsuarioResponseDTO;
import com.delivery_api.Projeto.Delivery.API.exception.ModelNotFoundException;
import com.delivery_api.Projeto.Delivery.API.model.Usuario;
import com.delivery_api.Projeto.Delivery.API.enums.Role;
import com.delivery_api.Projeto.Delivery.API.exception.BusinessException;
import com.delivery_api.Projeto.Delivery.API.repository.UsuarioRepository;
import com.delivery_api.Projeto.Delivery.API.security.JwtUtil;
import com.delivery_api.Projeto.Delivery.API.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if(usuarioRepository.existsByEmail(dto.getEmail())){
            throw new BusinessException("Email já cadastrado: " + dto.getEmail());
        }
        Usuario usuario = Usuario.builder()
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .nome(dto.getNome())
                .role(dto.getRole() != null ? dto.getRole() : Role.CLIENTE)
                .dataCriacao(LocalDateTime.now())
                .ativo(true)
                .restauranteId(dto.getRestauranteId())
                .build();
        usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioResponseDTO.class);

    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado após autenticação bem-sucedida."));

        UserDetails userDetails = User.withUsername(usuario.getEmail())
                .password("")
                .authorities("ROLE_" + usuario.getRole().name())
                .build();
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setUsuario(modelMapper.map(usuario, UsuarioResponseDTO.class));
        responseDTO.setTipo("Bearer");
        responseDTO.setExpiracao(86400000L); // 1 dia em segundos
        responseDTO.setToken(jwtUtil.generateToken(User.withUsername(usuario.getEmail()).password(usuario.getSenha()).authorities("ROLE_" + usuario.getRole().name()).build(), usuario));

        return responseDTO;

    }
    @Override
    public UsuarioResponseDTO getAuthenticatedUser() {
        // 1. Obter o UserDetails do contexto de segurança
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 2. Usar o email para buscar a entidade Usuario completa
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ModelNotFoundException("Usuário logado não encontrado no banco de dados."));

        // 3. Mapear e retornar
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }
}