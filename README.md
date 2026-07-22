# Hourly Pay Tracker API

API REST desenvolvida em Java com Spring Boot para gerenciar pagamentos por hora, usuários, empresas e registros de tempo. Este projeto foi estruturado com foco em organização, segurança, persistência de dados e boas práticas de desenvolvimento.

## Objetivo do projeto

A aplicação permite:
- cadastrar e autenticar usuários;
- gerenciar informações de pagamento de empresas;
- registrar entradas de tempo trabalhado;
- organizar a lógica de cálculo e persistência de dados em uma API robusta.

## Stack tecnológica

### Linguagem e runtime
- Java 21
- Spring Boot 3.5.6

### Frameworks e bibliotecas principais
- Spring Web MVC para construção da API REST
- Spring Data JPA para acesso a dados
- Spring Validation para validação de entradas
- Spring Security para autenticação e proteção dos endpoints
- JWT (JSON Web Token) para autenticação stateless
- Jackson para serialização/deserialização JSON
- MapStruct para mapeamento entre entidades e DTOs
- Lombok para reduzir boilerplate em modelos e serviços

### Banco de dados
- PostgreSQL
- Hibernate como implementação de ORM

### Ferramentas de build e automação
- Gradle
- Gradle Wrapper

### Testes
- JUnit 5
- Spring Boot Test

### Outras ferramentas e práticas
- Arquitetura em camadas: controller, service, repository, entity, dto, mapper
- Configuração de ambiente via variáveis de ambiente (.env)
- Estrutura preparada para evolução com segurança, manutenção e escalabilidade

## Estrutura do projeto

A API está organizada em módulos lógicos para facilitar manutenção e expansão:
- controller: endpoints da aplicação
- service: regras de negócio
- repository: acesso ao banco
- entity: modelos de dados
- dto: transferência de dados entre camadas
- mapper: conversão entre entidades e DTOs
- security: autenticação e autorização
- exception: tratamento de erros

## Segurança implementada

- autenticação via JWT;
- proteção de endpoints com Spring Security;
- configuração de filtros para validação de token;

## Como executar o projeto

### Pré-requisitos
- Java 21
- PostgreSQL rodando localmente
- variáveis de ambiente configuradas com:
  - DB_URL
  - DB_USERNAME
  - DB_PASSWORD
  - JWT_SECRET
  - JWT_EXPIRATION

### Comandos
```bash
./gradlew bootRun
```

## Como rodar os testes
```bash
./gradlew test
```

# Hourly Pay Tracker API English

## Project Overview

REST API developed in Java with Spring Boot to manage hourly payments, users, companies, and time entries. This project was structured with a focus on organization, security, data persistence, and development best practices.

The application allows:
- register and authenticate users;
- manage company payment information;
- record worked time entries;
- organize business logic and persistence in a robust API.

## Technology Stack

### Language and Runtime
- Java 21
- Spring Boot 3.5.6

### Main Frameworks and Libraries
- Spring Web MVC for building the REST API
- Spring Data JPA for data access
- Spring Validation for input validation
- Spring Security for authentication and endpoint protection
- JWT (JSON Web Token) for stateless authentication
- Jackson for JSON serialization and deserialization
- MapStruct for mapping between entities and DTOs
- Lombok to reduce boilerplate in models and services

### Database
- PostgreSQL
- Hibernate as the ORM implementation

### Build and Automation Tools
- Gradle
- Gradle Wrapper

### Testing
- JUnit 5
- Spring Boot Test

### Other Tools and Practices
- Layered architecture: controller, service, repository, entity, dto, mapper
- Environment configuration through environment variables (.env)
- Structure prepared for evolution with security, maintainability, and scalability

## Project Structure

The API is organized into logical modules to facilitate maintenance and expansion:
- controller: application endpoints
- service: business rules
- repository: database access
- entity: data models
- dto: data transfer between layers
- mapper: conversion between entities and DTOs
- security: authentication and authorization
- exception: error handling

## Security Implemented

- authentication with JWT;
- endpoint protection with Spring Security;
- filter configuration for token validation;

## How to Run the Project

### Prerequisites
- Java 21
- PostgreSQL running locally
- environment variables configured with:
  - DB_URL
  - DB_USERNAME
  - DB_PASSWORD
  - JWT_SECRET
  - JWT_EXPIRATION

### Commands
```bash
./gradlew bootRun
```

## How to Run the Tests
```bash
./gradlew test
```