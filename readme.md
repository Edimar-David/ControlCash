# ControlCash API

API REST para gerenciamento financeiro pessoal desenvolvida com Java e Spring Boot.

O ControlCash foi criado para permitir que cada usuário registre receitas, despesas e metas financeiras de forma segura, disponibilizando indicadores e resumos financeiros que auxiliam no acompanhamento da vida financeira.

A aplicação foi desenvolvida utilizando uma arquitetura em camadas, autenticação JWT baseada em cookies HTTP e consultas otimizadas com Spring Data JPA.

>  Projeto em desenvolvimento.

---

# Sobre o Projeto

Controlar gastos e acompanhar a evolução financeira costuma ser uma tarefa difícil quando as informações ficam distribuídas entre planilhas, aplicativos diferentes ou anotações.
O objetivo do ControlCash é concentrar essas informações em uma única API, permitindo que aplicações clientes consultem, registrem e organizem dados financeiros de forma segura e eficiente.

Além das operações básicas de cadastro, o sistema também fornece indicadores financeiros calculados diretamente no banco de dados, reduzindo processamento na aplicação e melhorando o desempenho das consultas.

---

# Funcionalidades

## Cadastro e autenticação

O sistema permite o cadastro de usuários e autenticação utilizando Spring Security.

Após o login é gerado um JWT que é enviado automaticamente em um cookie HTTP, sendo utilizado nas próximas requisições para identificar o usuário autenticado.

---

## Gerenciamento de transações

Cada usuário possui seu próprio conjunto de transações financeiras.

É possível:
- cadastrar receitas
- cadastrar despesas
- editar registros
- excluir movimentações
- consultar transações individualmente

Todas as operações respeitam o isolamento dos dados do usuário autenticado.

---

## Consultas com filtros e Paginação

As consultas de transações permitem combinar diferentes filtros, como:
- período
- categoria
- tipo de movimentação
- descrição
- ordenação

As consultas são construídas dinamicamente utilizando JPQL.

Os resultados são retornados de forma paginada, permitindo consultas eficientes mesmo com grande quantidade de registros.

O cliente pode definir:
- página
- quantidade de registros
- ordenação

---

## Resumo financeiro

A API disponibiliza informações consolidadas para facilitar a visualização da situação financeira do usuário.

São calculados:

- total de receitas
- total de despesas
- saldo

Essas agregações são processadas diretamente pelo banco de dados utilizando funções de agregação.

---

# Segurança

A autenticação foi desenvolvida utilizando Spring Security e JWT.

Entre as principais medidas implementadas estão:

- PasswordEncoder para armazenamento seguro das senhas
- autenticação baseada em JWT
- token enviado através de cookies HTTP
- rotas protegidas
- isolamento completo dos dados entre usuários
- filtros de autenticação do Spring Security

---

# Arquitetura

O projeto segue uma arquitetura em camadas para separar responsabilidades e facilitar manutenção.

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

### Camadas

#### Controller
Responsável por receber as requisições HTTP, validar parâmetros e retornar as respostas da API.

#### Service
Contém toda a regra de negócio da aplicação.

#### Repository
Responsável pela comunicação com o banco de dados utilizando Spring Data JPA.

#### DTO
Objetos utilizados para entrada e saída de dados da API.

#### Security
Implementação da autenticação, autorização e filtros de segurança.

#### Exception
Tratamento global das exceções retornadas pela API.

#### Config
Classes responsáveis pelas configurações do Spring.

---

# Tecnologias

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- JWT

---

# Estrutura do Projeto

src
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
└── util


---

# Endpoints

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| POST | /auth/register | Cadastro de usuário |
| POST | /auth/login | Login |
| POST | /transactions | Criar transação |
| GET | /transactions | Listar transações |
| GET | /transactions/{id} | Buscar transação |
| PUT | /transactions/{id} | Atualizar transação |
| DELETE | /transactions/{id} | Excluir transação |
| GET | /dashboard/summary | Resumo financeiro |

---

# Como executar

```bash
git clone https://github.com/...
cd controlcash-api

Configure o arquivo application.properties.

spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

Execute:

./mvnw spring-boot:run
Próximas funcionalidades
 Swagger/OpenAPI
 Docker
 Testes unitários
 Testes de integração
 Deploy
 CI/CD com GitHub Actions
