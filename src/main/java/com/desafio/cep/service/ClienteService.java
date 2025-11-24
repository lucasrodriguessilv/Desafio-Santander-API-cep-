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

        // Evitar problema: se ID vier 0, tratamos como null (novo cliente)
        if (cliente.getId() != null && cliente.getId() == 0) {
            cliente.setId(null);
        }

        // Verifica duplicidade de e-mail somente em criação
        if (cliente.getId() == null && clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new IllegalArgumentException("O e-mail informado já está cadastrado.");
        }

        return clienteRepository.save(cliente);
    }

    // LISTAR TODOS OS CLIENTES
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // BUSCAR POR ID
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    // UPDATE (PUT)
    public Cliente update(Long id, Cliente updatedCliente) {

        return clienteRepository.findById(id)
                .map(clienteExistente -> {

                    // Verifica se o novo e-mail já pertence a outro cliente
                    Cliente clienteComEmail = clienteRepository.findByEmail(updatedCliente.getEmail());
                    if (clienteComEmail != null && !clienteComEmail.getId().equals(id)) {
                        throw new IllegalArgumentException("O e-mail informado já está sendo usado por outro cliente.");
                    }

                    // Atualiza os dados
                    clienteExistente.setNome(updatedCliente.getNome());
                    clienteExistente.setEmail(updatedCliente.getEmail());
                    clienteExistente.setTelefone(updatedCliente.getTelefone());

                    return clienteRepository.save(clienteExistente);
                })
                .orElseThrow(() -> new IllegalArgumentException("Cliente com ID " + id + " não encontrado."));
    }

    // DELETE
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}
