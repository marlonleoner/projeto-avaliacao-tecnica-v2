# Objetivo

"No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução backend para gerenciar essas sessões de votação."

Objetivo: Implementação de uma solução para controlar sessões de votações de pautas, delimitando o tempo e persistindo tais informações.

# Ferramentas utilizadas

## Framework - Spring Boot

Para o desenvolvimento da aplicação, foi utilizado o framework Spring Boot.

## Banco de dados - SQLite

Para facilitar a execução deste projeto, foi utilizado o banco de dados SQLite. O SQLite é uma base de dados relacional de código aberto e que dispensa o uso de um servidor, armazenando seus arquivos dentro de sua própria estrutura.

## Mensageria - RabbitMQ

Para a comunicação asincrona, foi utilizado o RabbitMQ. Na mensageria é realizado o controle do status de uma sessão, tendo fluxos para abertura, fechamento e notificação das sessões.

## Testes - Mockito e JUnit

Para os testes unitários foi utilizado o Mockito e o JUnit.

## Documentação - Swagger

Para documentação, consumo e visualização da aplicação, foi utilizado Swagger.

# Execução do projeto

# Melhorias futuras

* Melhoria na cobertura de testes: objetivo de quase todo o código com cobertura de teste.