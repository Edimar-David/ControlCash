# Financial Dashboard API

API backend para gerenciamento de finanças pessoais, com autenticação segura e operações sobre transações financeiras.

## Status do projeto

Em desenvolvimento.

## Funcionalidades

- Autenticação com JWT (registro e login)
- Senhas criptografadas com Spring Security
- CRUD completo de transações
- Cálculo de resumo financeiro mensal (summary)

---

## Tecnologias

- Java 21  
- Spring Boot  
- Spring Security  
- JWT (JSON Web Token)  
- JPA / Hibernate  
- PostgreSQL  

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

---

### Summary mensal

Retorna um resumo financeiro baseado nas transações de um mês específico.

`GET /transactions/summary?month={month}&year={year}`

Exemplo:

```
http://localhost:8080/transactions/summary?month=6&year=2026
```

#### Parâmetros

- `month`: mês (1 a 12)  
- `year`: ano (ex: 2026)

#### Resposta

```json
{
  "totalTransactions": 8,
  "totalIncome": 2000,
  "totalExpense": 500,
  "totalBalance": 1500
}
```

Query:
```
        SELECT new com.novaStack.backend.DTO.SummaryDTO(
        COUNT(t) as totalTransactions,
        
        COALESCE(SUM(CASE WHEN t.type = 'INCOME' then t.amount
        ELSE 0 END), 0) AS totalIncome,

        COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' then t.amount
        ELSE 0 END),0) AS totalExpense,

        COALESCE(SUM(CASE WHEN t.type = 'INCOME'	THEN t.amount WHEN t.type = 'EXPENSE'
        THEN -t.amount ELSE 0 END), 0) AS totalBalance
    )
    FROM Transaction t
        WHERE t.date < :end
        AND t.date >= :start 
        AND t.user = :user 
```
#### Regras

- Considera apenas transações do mês informado  
- `totalTransactions` representa a quantidade de transações no período  
- `totalIncome` soma apenas entradas  
- `totalExpense` soma apenas saídas  
- `totalBalance` é o resultado de income - expense  

---

## Segurança

- Senhas com hash usando PasswordEncoder  
- Autenticação via JWT  
- Rotas protegidas por autenticação  
- Isolamento de dados por usuário  

---

## Como executar

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
./mvnw spring-boot:run
```

---

## Observações

Este projeto tem como foco a construção de uma API backend com regras de negócio reais, incluindo segurança, organização e separação entre dados por usuário.
