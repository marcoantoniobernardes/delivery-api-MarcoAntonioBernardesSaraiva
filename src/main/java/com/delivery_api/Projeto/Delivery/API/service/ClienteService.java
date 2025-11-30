package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.model.Cliente;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // --- CORREÇÃO DE IMPLEMENTAÇÃO ---

    /**
     * Cadastra um novo cliente, verificando a duplicidade de e-mail.
     */
    @Transactional
    public Cliente cadastrar(Cliente cliente) {
        // Verifica se o e-mail já existe antes de salvar
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        // Salva o cliente no banco de dados
        cliente.setAtivo(true); // Garante que o cliente é ativo ao cadastrar
        return clienteRepository.save(cliente);
    }

    /**
     * Lista todos os clientes que estão ativos.
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarAtivos() {
        // Usa o método derivado 'findByAtivoTrue'
        return clienteRepository.findByAtivoTrue();
    }

    /**
     * Busca cliente por ID. (Já estava correto)
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Atualiza os dados de um cliente existente.
     */
    @Transactional
    public Cliente atualizar(Long id, Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        // Implementação básica de atualização:
        cliente.setNome(clienteDetails.getNome());
        cliente.setTelefone(clienteDetails.getTelefone());
        cliente.setEndereco(clienteDetails.getEndereco());

        // Regra: Não permitir alteração do e-mail se já existir outro cadastro com ele
        if (!cliente.getEmail().equals(clienteDetails.getEmail()) &&
                clienteRepository.existsByEmail(clienteDetails.getEmail())) {
            throw new IllegalArgumentException("Novo e-mail já está sendo usado por outro cliente.");
        }
        cliente.setEmail(clienteDetails.getEmail());

        return clienteRepository.save(cliente);
    }

    /**
     * Inativa o cliente (Soft Delete).
     */
    @Transactional
    public void inativar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        cliente.inativar(); // Chamando o método 'inativar()' da Entidade
        clienteRepository.save(cliente);
    }

    /**
     * Busca clientes por nome (parcial e ignorando case).
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        // Usa o método derivado 'findByNomeContainingIgnoreCase'
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Busca cliente por e-mail. (Já estava correto)
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
}