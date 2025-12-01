package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.request.LoginRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.request.UsuarioRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.LoginResponseDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.UsuarioResponseDTO;
import com.delivery_api.Projeto.Delivery.API.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO login = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(login);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais ou Token inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO login = usuarioService.login(dto);
        return ResponseEntity.ok(login);
    }
    @GetMapping("/me") // Mapeia para GET /auth/me
    @PreAuthorize("isAuthenticated()") // Garante que o endpoint é protegido
    public ResponseEntity<UsuarioResponseDTO> getAuthenticatedUser() {
        // 1. Usa o SecurityContextHolder para obter o UserDetails do usuário logado
        // (Você precisará de um método utilitário para converter UserDetails para UsuarioResponseDTO)

        // Supondo que você use um SecurityUtils:
        UsuarioResponseDTO usuario = usuarioService.getAuthenticatedUser();

        return ResponseEntity.ok(usuario);
    }
}