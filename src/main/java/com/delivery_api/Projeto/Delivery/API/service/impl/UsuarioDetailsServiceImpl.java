package com.delivery_api.Projeto.Delivery.API.service.impl;

import com.delivery_api.Projeto.Delivery.API.model.Usuario;
import com.delivery_api.Projeto.Delivery.API.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
@RequiredArgsConstructor
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailAndAtivo(email, true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado ou inativo: " + email));

        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getAuthorities() // Usa o método corrigido da Entidade
        );
    }
}
