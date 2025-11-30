package com.delivery_api.Projeto.Delivery.API.service.impl;

import com.delivery_api.Projeto.Delivery.API.dto.request.RestauranteRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.ProdutoResponseDTO;
import com.delivery_api.Projeto.Delivery.API.dto.response.RestauranteResponseDTO;
import com.delivery_api.Projeto.Delivery.API.exception.ModelNotFoundException;
import com.delivery_api.Projeto.Delivery.API.model.Produto;
import com.delivery_api.Projeto.Delivery.API.model.Restaurante;
import com.delivery_api.Projeto.Delivery.API.exception.BusinessException;
import com.delivery_api.Projeto.Delivery.API.projection.RelatorioVendas;
import com.delivery_api.Projeto.Delivery.API.repository.ProdutoRepository;
import com.delivery_api.Projeto.Delivery.API.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.delivery_api.Projeto.Delivery.API.service.RestauranteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<RestauranteResponseDTO> buscarComFiltros(String categoria, Boolean ativo, Pageable pageable) {
        Page<Restaurante> restaurantesPage;

        if (categoria != null && ativo != null) {
            // Usa o método do Repositório que combina os filtros e a paginação
            restaurantesPage = restauranteRepository.findByCategoriaAndAtivo(categoria, ativo, pageable);
        }
        else {
            // Fallback
            restaurantesPage = restauranteRepository.findAll(pageable);
        }

        return restaurantesPage.map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class));
    }

    @Override
    public RestauranteResponseDTO cadastrar(RestauranteRequestDTO dto) {
        Optional<Restaurante> byNome = restauranteRepository.findByNome(dto.getNome());
        if (byNome.isPresent()) {
            throw new BusinessException("Restaurante já cadastrado: " + dto.getNome());
        }
        // Converter DTO para entidade
        Restaurante restaurante = modelMapper.map(dto, Restaurante.class);
        // Salvar cliente
        Restaurante restauranteSalvo = restauranteRepository.save(restaurante);
        // Retornar DTO de resposta
        return modelMapper.map(restauranteSalvo, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO buscarPorId(Long id) {
        // Buscar restaurante por ID
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Restaurante não encontrado com ID: " + id));
        // Converter entidade para DTO
        return modelMapper.map(restaurante, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO atualizar(Long id, RestauranteRequestDTO dto) {
        // Buscar restaurante existente
        Restaurante restauranteExistente = restauranteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: " + id));

        // Atualizar campos do restaurante
        restauranteExistente.setNome(dto.getNome());
        restauranteExistente.setCategoria(dto.getCategoria());
        restauranteExistente.setTelefone(dto.getTelefone());
        restauranteExistente.setAvaliacao(dto.getAvaliacao());
        restauranteExistente.setEndereco(dto.getEndereco());
        restauranteExistente.setTelefone(dto.getTelefone());

        // Salvar as alterações
        Restaurante restauranteAtualizado = restauranteRepository.save(restauranteExistente);

        // Retornar DTO atualizado
        return modelMapper.map(restauranteAtualizado, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO ativarDesativarRestaurante(Long id) {
        // Buscar restaurante por ID
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: " + id));
        // Alternar status de ativo/desativado
        restaurante.setAtivo(!restaurante.getAtivo());
        // Salvar as alterações
        Restaurante restauranteAtualizado = restauranteRepository.save(restaurante);
        // Retornar DTO atualizado
        return modelMapper.map(restauranteAtualizado, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO buscarPorNome(String nome) {
        Restaurante restaurante = restauranteRepository.findByNomeAndAtivoTrue(nome);

        if (restaurante == null) {
            throw new BusinessException("Restaurante não encontrado com nome: " + nome);
        }

        return modelMapper.map(restaurante, RestauranteResponseDTO.class);
    }

    @Override
    public List<RestauranteResponseDTO> buscarPorCategoria(String categoria) {
        // Buscar restaurantes por categoria
        List<Restaurante> restaurantes = restauranteRepository.findByCategoria(categoria);
        if (restaurantes.isEmpty()) {
            throw new BusinessException("Nenhum restaurante encontrado na categoria: " + categoria);
        }
        // Converter lista de entidades para lista de DTOs
        return restaurantes.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public List<RestauranteResponseDTO> buscarPorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        // Buscar restaurantes por taxa de entrega dentro do intervalo
        List<Restaurante> restaurantes = restauranteRepository.findByTaxaEntregaBetween(precoMinimo, precoMaximo);
        if (restaurantes.isEmpty()) {
            throw new BusinessException("Nenhum restaurante encontrado com taxa de entrega entre " + precoMinimo + " e " + precoMaximo);
        }
        // Converter lista de entidades para lista de DTOs
        return restaurantes.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public List<RestauranteResponseDTO> listarAtivos() {
        // Buscar todos os restaurantes ativos
        List<Restaurante> restaurantesAtivos = restauranteRepository.findByAtivoTrue();
        if (restaurantesAtivos.isEmpty()) {
            throw new BusinessException("Nenhum restaurante ativo encontrado.");
        }
        // Converter lista de entidades para lista de DTOs
        return restaurantesAtivos.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public List<RestauranteResponseDTO> listarTop5PorNome() {
        // Buscar os 5 primeiros restaurantes por nome
        List<Restaurante> top5Restaurantes = restauranteRepository.findTop5ByOrderByNomeAsc();
        if (top5Restaurantes.isEmpty()) {
            throw new BusinessException("Nenhum restaurante encontrado.");
        }
        // Converter lista de entidades para lista de DTOs
        return top5Restaurantes.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public List<RelatorioVendas> relatorioVendasPorRestaurante() {
        // Buscar relatório de vendas por restaurante
        List<RelatorioVendas> relatorio = restauranteRepository.relatorioVendasPorRestaurante();
        if (relatorio.isEmpty()) {
            throw new BusinessException("Nenhum dado de vendas encontrado.");
        }
        // Converter lista de entidades para lista de DTOs
        return relatorio.stream()
                .map(restaurante -> modelMapper.map(restaurante, RelatorioVendas.class))
                .toList();
    }

    @Override
    public List<RestauranteResponseDTO> buscarPorTaxaEntrega(BigDecimal taxaEntrega) {
        // Buscar restaurantes por taxa de entrega
        List<Restaurante> restaurantes = restauranteRepository.findByTaxaEntregaLessThanEqual(taxaEntrega);
        if (restaurantes.isEmpty()) {
            throw new BusinessException("Nenhum restaurante encontrado com taxa de entrega menor ou igual a: " + taxaEntrega);
        }
        // Converter lista de entidades para lista de DTOs
        return restaurantes.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }
    @Override
    public RestauranteResponseDTO inativarRestaurante(Long id) {
        // Buscar restaurante por ID
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: " + id));
        // Verificar se o restaurante já está inativo
        if (!restaurante.getAtivo()) {
            throw new BusinessException("Restaurante já está inativo: " + restaurante.getNome());
        }
        // Inativar o restaurante
        restaurante.setAtivo(false);
        // Salvar as alterações
        Restaurante restauranteInativado = restauranteRepository.save(restaurante);
        // Retornar DTO atualizado
        return modelMapper.map(restauranteInativado, RestauranteResponseDTO.class);
    }
    @Override
    public List<ProdutoResponseDTO> listarProdutosPorRestaurante(Long restauranteId, Boolean disponivel) {
        List<Produto> produtos;

        if (disponivel != null) {
            produtos = produtoRepository.findByRestauranteIdAndDisponivel(restauranteId, disponivel);
        } else {
            produtos = produtoRepository.findByRestauranteId(restauranteId);
        }

        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }
}