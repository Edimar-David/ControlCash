# Financial Dashboard API

API backend para gerenciamento de finanças pessoais, com autenticação segura, regras de negócio e suporte a filtros dinâmicos com paginação.

## Status do projeto

Em desenvolvimento.

---

## Funcionalidades

- Autenticação com JWT (registro e login)
- Senhas criptografadas com Spring Security
- CRUD completo de transações
- Filtro dinâmico por data e tipo
- Paginação de resultados
- Cálculo de resumo financeiro mensal (summary)
- Isolamento de dados por usuário

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

Authorization: Bearer {token}

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
Login


`POST /auth/login`
```json
{
  "email": "email@email.com",
  "password": "123456"
}
```

Transações

Base URL: `/transaction`

Criar transação

`POST /transaction`

Listar todas

`GET /transaction`

Buscar por ID

`GET /transaction/{id}`

Atualizar

`PUT /transaction/{id}`

Deletar

`DELETE /transaction/{id}`

---

## Filtro + Paginação (NOVO)

Permite buscar transações com filtros opcionais e paginação.

GET `/transaction/filter`

```
Query Params (todos opcionais)
Parâmetro |	Tipo     	| Descrição
startDate |	LocalDate   |	Data inicial (YYYY-MM-DD)
endDate	  |LocalDate	| Data final (YYYY-MM-DD)
type	  | ENUM	    | INCOME ou EXPENSE
page	  | Integer	    | Página (default: 0)
size      |	Integer	    | Tamanho da página (default: 10)
```
exemplo:
`http://localhost:8080/transaction/filter?startDate=2026-06-01&endDate=2026-06-30&type=INCOME&page=0&size=10`
Comportamento:
-Todos os filtros são opcionais
-A query é construída dinamicamente no backend
-Os resultados são ordenados por data (mais recente primeiro)
*Paginação feita via setFirstResult e setMaxResults
## resposta
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

## Summar mensal

Retorna um resumo financeiro baseado nas transações de um mês específico.
GET `/transaction/summary?month={month}&year={year}`
exemplo:
`http://localhost:8080/transaction/summary?month=6&year=2026`
resposta:
```json
{
  "totalTransactions": 8,
  "totalIncome": 2000,
  "totalExpense": 500,
  "totalBalance": 1500
}
```
## Regras
- Considera apenas transações do mês informado
- totalTransactions: quantidade total
- totalIncome: soma de entradas
- totalExpense: soma de saídas
- totalBalance: income - expense

---

## Segurança
- Senhas com hash usando PasswordEncoder
- Autenticação via JWT
- Rotas protegidas
- Isolamento de dados por usuário
  
  ---
  ##Como executar
  ```
  git clone https://github.com/seu-usuario/seu-repositorio.git
  cd seu-repositorio
  ./mvnw spring-boot:run
  
  ```

  ### Observações

Este projeto tem como foco simular um backend real de mercado, incluindo:

- Construção de queries dinâmicas
- Paginação eficiente
- Separação de responsabilidades (Controller, Service, DTO)
- Regras de negócio aplicadas

  ---

  ## Próximas melhorias
- Retornar Page<T> ao invés de List<T> (melhor prática)
- Adicionar total de páginas no response
- Implementar Specification (Criteria API) ao invés de JPQL manual
- Validação com Bean Validation
- Tratamento global de exceções
- Testes automatizados
  ---
  
