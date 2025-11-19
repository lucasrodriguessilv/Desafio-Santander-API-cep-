package com.desafio.cep.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class ConsultaLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;

    @Column(columnDefinition = "TEXT")
    private String respostaApi;

    private LocalDateTime dataConsulta;
}
