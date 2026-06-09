# Financial Dashboard API

API REST para gerenciamento de finanças pessoais, desenvolvida com Spring Boot. O projeto implementa autenticação segura, isolamento de dados por usuário, filtros dinâmicos, paginação e geração de resumos financeiros.

## Status

Em desenvolvimento

---

## Funcionalidades

* Cadastro e autenticação de usuários
* Autenticação baseada em JWT
* Armazenamento do token JWT em cookie seguro
* Senhas criptografadas com Spring Security
* CRUD completo de transações financeiras
* Filtros dinâmicos por período e tipo
* Paginação de resultados
* Resumo financeiro mensal
* Isolamento de dados por usuário autenticado

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

## Auth

### Registrar usuário

`POST /auth/register`

```json
{
  "name": "Seu Nome",
  "email": "email@email.com",
  "password": "123456"
}
```

### Login

`POST /auth/login`

```json
{
  "email": "email@email.com",
  "password": "123456"
}
```

---

## Transações

Base URL:

```http
/transaction
```

### Criar transação

```http
POST /transaction
```

### Listar transações

```http
GET /transaction
```

### Buscar por ID

```http
GET /transaction/{id}
```

### Atualizar transação

```http
PUT /transaction/{id}
```

### Remover transação

```http
DELETE /transaction/{id}
```

---

## Filtros e Paginação

Permite buscar transações utilizando filtros opcionais.

### Endpoint

```http
GET /transaction/filter
```

### Query Params

| Parâmetro | Tipo      | Descrição                           |
| --------- | --------- | ----------------------------------- |
| startDate | LocalDate | Data inicial (YYYY-MM-DD)           |
| endDate   | LocalDate | Data final (YYYY-MM-DD)             |
| type      | ENUM      | INCOME ou EXPENSE                   |
| page      | Integer   | Página (default: 0)                 |
| size      | Integer   | Quantidade por página (default: 10) |

### Regras

* Todos os filtros são opcionais
* Consulta construída dinamicamente no backend
* Ordenação por data decrescente
* Paginação aplicada diretamente na consulta

### Exemplo

```http
GET /transaction/filter?startDate=2026-06-01&endDate=2026-06-30&type=INCOME&page=0&size=10
```

### Resposta

```json
[
  {
    "id": 1,
    "type": "INCOME",
    "description": "Salário",
    "amount": 2000,
    "category": "Trabalho",
    "date": "2026-06-10"
  }
]
```

---

## Resumo Mensal

Retorna um resumo financeiro para um determinado mês.

### Endpoint

```http
GET /transaction/summary?month={month}&year={year}
```

### Exemplo

```http
GET /transaction/summary?month=6&year=2026
```

### Resposta

```json
{
  "totalTransactions": 8,
  "totalIncome": 2000,
  "totalExpense": 500,
  "totalBalance": 1500
}
```

### Regras

* Considera apenas transações do mês informado
* totalTransactions → quantidade de transações
* totalIncome → soma das entradas
* totalExpense → soma das saídas
* totalBalance → saldo final (income - expense)

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

---

## Próximas Melhorias

* Retornar Page<T> em vez de List<T>
* Adicionar metadados de paginação
* Implementar Specification (Criteria API)
* Bean Validation
* Tratamento global de exceções
* Testes automatizados
* Documentação com Swagger/OpenAPI
