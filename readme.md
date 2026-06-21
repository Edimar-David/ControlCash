# ControlCash API

Sistema de gestão financeira pessoal construído com Java e Spring Boot.

A aplicação disponibiliza uma API REST segura para gerenciamento financeiro pessoal, oferecendo autenticação JWT, isolamento de dados por usuário, consultas dinâmicas com JPQL, paginação e geração de indicadores financeiros para acompanhamento de receitas, despesas e metas.

## Status

- Em desenvolvimento
- Demonstração pública ainda não disponível

---

## Sobre o Projeto

Muitas pessoas registram receitas e despesas de forma dispersa ou não possuem uma visão consolidada de sua situação financeira.

O ControlCash foi desenvolvido para centralizar o gerenciamento financeiro pessoal em uma única plataforma, permitindo o acompanhamento de gastos, receitas e metas financeiras por meio de dashboards e relatórios que facilitam a tomada de decisões.

--- 

## Funcionalidades

- Cadastro e autenticação de usuários
- Controle de acesso com JWT
- CRUD completo de transações financeiras
- Paginação com filtros dinâmicos para consultas de transações
- Consultas dinâmicas utilizando JPQL
- Agregações financeiras processadas diretamente no banco de dados
- Resumo financeiro mensal
- Isolamento de dados por usuário

---
# Arquitetura


 
---

## Tecnologias

- Java 21
- Spring Boot
- Maven
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- PostgreSQL

---
# Estrutura do Projeto


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
