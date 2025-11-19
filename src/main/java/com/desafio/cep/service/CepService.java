package com.desafio.cep.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.desafio.cep.client.ViaCepClient;
import com.desafio.cep.model.CepResponse;
import com.desafio.cep.model.ConsultaLog;
import com.desafio.cep.repository.ConsultaLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CepService implements ICepService {

    @Autowired
    private ViaCepClient viaCepClient;

    @Autowired
    private ConsultaLogRepository logRepository;

    @Autowired
    private ConsultaLogRepository consultaLogRepository;


    @Override
    public ConsultaLog criar(ConsultaLog log) {
        log.setId(null); // garante criação
        log.setDataConsulta(LocalDateTime.now());
        return consultaLogRepository.save(log);
    }


    @Override
    public CepResponse buscarCep(String cep) {
        CepResponse resposta = viaCepClient.buscarCep(cep);
        salvarLog(cep, resposta);
        return resposta;
    }

    @Override
    public List<ConsultaLog> listarLogs() {
        return logRepository.findAll();
    }

    @Override
    public ConsultaLog buscarPorId(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Log não encontrado"));
    }

    @Override
    public ConsultaLog atualizar(Long id, ConsultaLog novoLog) {
        ConsultaLog existente = buscarPorId(id);

        existente.setCep(novoLog.getCep());
        existente.setRespostaApi(novoLog.getRespostaApi());
        existente.setDataConsulta(novoLog.getDataConsulta());

        return logRepository.save(existente);
    }

    @Override
    public void deletar(Long id) {
        if (!logRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado");
        }
        logRepository.deleteById(id);
    }

    private void salvarLog(String cep, CepResponse resposta) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String respostaJson = mapper.writeValueAsString(resposta);

            ConsultaLog log = new ConsultaLog();
            log.setCep(cep);
            log.setRespostaApi(respostaJson);
            log.setDataConsulta(LocalDateTime.now());

            logRepository.save(log);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
