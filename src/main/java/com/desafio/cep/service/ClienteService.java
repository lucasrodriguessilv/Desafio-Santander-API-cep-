package com.desafio.cep.service;

import com.desafio.cep.model.Cliente;
import com.desafio.cep.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // CREATE (POST)
    public Cliente save(Cliente cliente) {
        // Exemplo de lógica de negócio: Garantir que o email não seja duplicado
        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new IllegalArgumentException("O e-mail informado já está cadastrado.");
        }
        return clienteRepository.save(cliente);
    }

    // LISTAR TODOS OS CLIENTES (GET)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // LISTAR POR ID (GET)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    // ATUALIZAR (PUT) - Usa o método save, mas com verificação de existência
    public Cliente update(Long id, Cliente updatedCliente) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    // Mapeamento de campos
                    clienteExistente.setNome(updatedCliente.getNome());
                    clienteExistente.setEmail(updatedCliente.getEmail());
                    clienteExistente.setTelefone(updatedCliente.getTelefone());
                    return clienteRepository.save(clienteExistente);
                })
                .orElseThrow(() -> new IllegalArgumentException("Cliente com ID " + id + " não encontrado."));
    }

    // EXCUIR (DELETE)
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}