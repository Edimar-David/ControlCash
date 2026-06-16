# Financial Dashboard API

API REST para gerenciamento de finanças pessoais, desenvolvida com Spring Boot. O projeto implementa autenticação segura, isolamento de dados por usuário, filtros dinâmicos, paginação e geração de resumos financeiros.

## Status

Em desenvolvimento

---

## Funcionalidades



---

## Tecnologias

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* Hibernate
* PostgreSQL

---

## Autenticação

A API utiliza JWT armazenado em cookie.

Após o login ou registro, o backend envia automaticamente um cookie contendo o token de autenticação.

As rotas protegidas utilizam esse cookie para identificar o usuário autenticado, eliminando a necessidade de armazenar tokens no localStorage do frontend.

### Benefícios

* Maior segurança contra exposição do token
* Menor risco de acesso indevido via JavaScript
* Fluxo de autenticação mais próximo de aplicações utilizadas em produção

---

# Endpoints

---

## Segurança

* Senhas protegidas com PasswordEncoder
* JWT armazenado em cookie
* Rotas protegidas por autenticação
* Isolamento de dados por usuário
* Filtros de segurança via Spring Security

---

## Como Executar

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git

cd seu-repositorio

./mvnw spring-boot:run
```

---

## Arquitetura

O projeto segue uma separação de responsabilidades baseada em:

* Controller
* Service
* Repository
* DTO
* Security
