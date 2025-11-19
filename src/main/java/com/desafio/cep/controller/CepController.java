package com.desafio.cep.controller;

import com.desafio.cep.model.CepResponse;
import com.desafio.cep.model.ConsultaLog;
import com.desafio.cep.service.ICepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cep")
public class CepController {

    @Autowired
    private ICepService cepService;


    // Criar um novo log manualmente
    @PostMapping("/logs")
    public ConsultaLog criar(@RequestBody ConsultaLog log) {
        return cepService.criar(log);
    }

    // Buscar CEP na API -
    @GetMapping("/{cep}")
    public CepResponse buscarCep(@PathVariable String cep) {
        return cepService.buscarCep(cep);
    }


    // Listar todos os logs
    @GetMapping("/logs")
    public List<ConsultaLog> listarLogs() {
        return cepService.listarLogs();
    }

    // Buscar log por ID
    @GetMapping("/logs/{id}")
    public ConsultaLog buscarPorId(@PathVariable Long id) {
        return cepService.buscarPorId(id);
    }

    // Deletar log por ID
    @DeleteMapping("/logs/{id}")
    public String deletar(@PathVariable Long id) {
        cepService.deletar(id);
        return "Log deletado com sucesso!";
    }

    // Atualizar log por ID
    @PutMapping("/logs/{id}")
    public ConsultaLog atualizar(@PathVariable Long id, @RequestBody ConsultaLog novoLog) {
        return cepService.atualizar(id, novoLog);
    }
}
