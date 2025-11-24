@echo off
REM Script para rodar WireMock (via docker-compose) e executar testes no Windows CMD
REM Execute a partir da raiz do projeto: C:\Users\Lucas\Downloads\cep

echo Verificando status do Docker...
docker ps
if %ERRORLEVEL% NEQ 0 (
  echo Erro: Docker nao parece estar rodando. Abra o Docker Desktop e tente novamente.
  pause
  exit /b 1
)

echo Subindo WireMock (docker compose)...
docker compose up -d
if %ERRORLEVEL% NEQ 0 (
  echo Erro ao subir WireMock com docker compose.
  pause
  exit /b 1
)

echo Executando teste com Testcontainers (WireMock em container)...
mvn -Dtest=com.desafio.cep.CepE2EWireMockContainerTest test
if %ERRORLEVEL% EQU 0 (
  echo Teste com WireMock via Testcontainers passou com sucesso.
) else (
  echo Teste com WireMock via Testcontainers falhou. Voce pode tentar rodar o teste sem Docker (MockRestServiceServer):
  echo mvn -Dtest=com.desafio.cep.CepE2ETest test
)

echo Fim.
pause
