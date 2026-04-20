# Financial Dashboard API

Backend de um dashboard financeiro com foco em autenticação segura e gerenciamento de transações.

## Status do projeto

Em desenvolvimento.

Atualmente implementado:
- Sistema de autenticação com JWT (login e registro)
- Criptografia de senha com Spring Security

Em andamento:
- CRUD de transações
- Proteção completa das rotas com autenticação

---

## Tecnologias

- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- JPA / Hibernate
- Banco de dados PostgreSQL
---

## Autenticação

A API utiliza autenticação baseada em JWT. Após login ou registro, é retornado um token que deve ser enviado nas próximas requisições.

### Registro

POST /auth/register

Request:
```json
{
  "name": "Seu Nome",
  "email": "email@email.com",
  "password": "123456"
}
```

Response:
```json
{
  "name": "Seu Nome",
  "token": "jwt_token_aqui"
}
```

---

### Login

POST /auth/login

Request:
```json
{
  "email": "email@email.com",
  "password": "123456"
}
```

Response:
```json
{
  "name": "Seu Nome",
  "token": "jwt_token_aqui"
}
```

---

## Transações

Endpoint disponível:

POST /transaction

Request:
```json
{
  // definido em TransactionRequestDTO
}
```

Response:
```json
{
  // definido em TransactionResponseDTO
}
```

Endpoints planejados:
- Listar transações do usuário
- Buscar transação por ID
- Atualizar transação
- Deletar transação

---

## Segurança

- Senhas armazenadas com hash (PasswordEncoder)
- Autenticação via JWT
- Rotas protegidas (em implementação completa)

---

## Como executar

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git

cd seu-repositorio

./mvnw spring-boot:run
```

---

## Próximos passos

- Finalizar CRUD de transações
- Aplicar autenticação nas rotas protegidas
- Adicionar validações nos DTOs
- Melhorar tratamento de erros
- Implementar testes
- Realizar deploy

---

## Observações

Este projeto ainda está em fase inicial. O foco atual é consolidar a base de autenticação e estruturar corretamente o backend antes de evoluir para funcionalidades mais completas.
