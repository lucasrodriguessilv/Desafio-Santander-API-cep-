package com.desafio.cep.service;

import com.desafio.cep.model.CepResponse;
import com.desafio.cep.model.ConsultaLog;
import java.util.List;

public interface ICepService {

    CepResponse buscarCep(String cep);

    void deletar(Long id);

    List<ConsultaLog> listarLogs();

    ConsultaLog buscarPorId(Long id);

    ConsultaLog atualizar(Long id, ConsultaLog novoLog);
}
