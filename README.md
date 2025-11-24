## Desenho da Solução
Visão Geral da Aplicação

A aplicação realiza busca de CEP em uma API externa (ViaCEP ou API mockada com WireMock/Mockoon) e registra logs das consultas em banco de dados H2.
Utiliza arquitetura em camadas e princípios SOLID.

[Controller]
     
[Service] ----→ [ViaCepClient (API externa]
     
[Repository]
     
[H2 Database]

# Fluxo da Busca de CEP
<br> Cliente chama GET /cep/{cep}
<br> CepController delega para CepService
<br> O serviço cria o registro do log
<br> ViaCepClient consulta a API externa (ou mock)
<br> O log é atualizado com resposta, horário e status
<br> O resultado é retornado ao cliente
# Componentes
<br> Controller: recebe requisições REST
<br> Service: regra de negócio + criação dos logs
<br> Client: comunicação com a API externa (real ou mockada)
<br> Repository: persistência dos logs e entidades
<br> H2 Database: armazenamento das consultas em memória
# API Mockada (WireMock)
<br> A aplicação pode usar mock apontando o client para:
<br> http://localhost:8089/ws/{cep}/json
# Armazenamento dos Logs
<br> Cada consulta salva:
<br> CEP solicitado
<br> Payload retornado
<br> Horário da consulta
