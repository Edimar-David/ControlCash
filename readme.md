# Financial Dashboard API

API backend para gerenciamento de finanças pessoais, com autenticação segura e operações CRUD de transações.

## Status do projeto

Em desenvolvimento.

### Funcionalidades atuais

* Autenticação com JWT (registro e login)
* Senhas criptografadas com Spring Security
* CRUD completo de transações

### Próximas melhorias

* Validação de dados nos DTOs
* Tratamento global de erros
* Testes automatizados
* Deploy

---

## Tecnologias

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* JPA / Hibernate
* PostgreSQL

---

## Autenticação

A API utiliza JWT para autenticação.

Após login ou registro, um token é retornado e deve ser enviado no header das requisições protegidas:

```
Authorization: Bearer {token}
```

---

## Endpoints

### Auth

#### Registro

`POST /auth/register`

```json
{
  "name": "Seu Nome",
  "email": "email@email.com",
  "password": "123456"
}
```

#### Login

`POST /auth/login`

```json
{
  "email": "email@email.com",
  "password": "123456"
}
```

---

### Transações

Base URL: `/transactions`

#### Criar transação

`POST /transactions`

#### Listar transações

`GET /transactions`

#### Buscar por ID

`GET /transactions/{id}`

#### Atualizar

`PUT /transactions/{id}`

#### Deletar

`DELETE /transactions/{id}`

> Os formatos de request/response seguem os DTOs definidos na aplicação.

---

## Segurança

* Senhas com hash usando `PasswordEncoder`
* Autenticação via JWT
* Rotas protegidas por autenticação

---

## Como executar

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git

cd seu-repositorio

./mvnw spring-boot:run
```

---

## Observações

Este projeto está em evolução e tem como foco consolidar boas práticas de backend com Spring Boot, incluindo segurança, organização e padrões REST.
