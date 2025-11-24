package com.desafio.cep.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CepDetails {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
}