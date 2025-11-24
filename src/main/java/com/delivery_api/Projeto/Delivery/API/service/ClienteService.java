package com.delivery_api.Projeto.Delivery.API.service;



import com.delivery_api.Projeto.Delivery.API.model.Cliente;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente cadastrar(Cliente cliente) {
        Cliente cliente1 = new Cliente();
        return cliente1;
    }

    public List<Cliente> listarAtivos() {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return Optional.of(new Cliente());
    }
    public Cliente atualizar(Long id, Cliente cliente) {
        return new Cliente();
    }

    public void inativar(Long id) {
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
            return new ArrayList<>();
    }
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return Optional.of(new Cliente());
    }
}
