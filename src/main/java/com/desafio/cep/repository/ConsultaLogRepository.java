package com.desafio.cep.repository;

import com.desafio.cep.model.ConsultaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaLogRepository extends JpaRepository<ConsultaLog, Long> {
}
