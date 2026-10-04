# Guia de Execução e Deploy Local — CIT-Ticket

Este documento contém as instruções detalhadas para configuração e execução do projeto CIT-Ticket (Backend, Frontend e Banco de Dados) em ambiente local, cumprindo os requisitos de avaliação técnica. O projeto foi estruturado para rodar nativamente, sem a necessidade de containers Docker.

---

## 1. Pré-requisitos

Para rodar o projeto localmente, certifique-se de ter as seguintes ferramentas instaladas:
* **Linguagem (Backend):** Java 17+ (JDK)
* **Linguagem (Frontend):** Node.js 18+ e NPM
* **Banco de Dados:** PostgreSQL 15+
* **Cliente de Banco de Dados (Opcional):** DBeaver, pgAdmin ou similar.

---

## 2. Configuração do Banco de Dados

A aplicação utiliza o **Flyway** para o controle de versionamento do banco de dados. Você não precisa criar as tabelas manualmente, apenas o banco vazio.

1. Inicie o serviço do PostgreSQL localmente.
2. Acesse seu banco (via DBeaver, pgAdmin ou CLI) e crie um banco de dados com o exato nome abaixo:
   ```sql
   CREATE DATABASE teste_tecnico_joao_pessoa;
   ```
*(Nota: Ao iniciar o backend, o Flyway rodará as migrations automaticamente e populará as tabelas necessárias).*

---

## 3. Instalação, Configuração e Execução do Backend

1. Clone o repositório do backend e acesse a pasta raiz do projeto via terminal.
2. **Variáveis de Ambiente:** O projeto exige três variáveis de ambiente obrigatórias para se conectar ao banco e assinar os tokens JWT. Configure-as na sua IDE (ex: IntelliJ Environment Variables) ou exporte no terminal antes de rodar. 

   Para facilitar os testes de validação, utilize os seguintes valores de demonstração:
   * `DATABASE_USERNAME`: `postgres`
   * `DATABASE_PASSWORD`: `123`
   * `JWT_SECRET`: `KH/iJllqqz9fwuPvpBmASuifqHlajszrvga/8V8lhDbbCARkzndYzmdCOGZ2aRJM`

3. Com as variáveis configuradas, execute o projeto usando o Maven Wrapper:
   ```bash
   # No Windows
   ./mvnw.cmd spring-boot:run
   
   # No Linux/Mac
   ./mvnw spring-boot:run
   ```
   *A API estará disponível na porta `8080`.*

---

## 4. Instalação e Execução do Frontend

1. Clone o repositório do frontend e acesse a pasta do projeto via terminal.
2. Instale as dependências:
   ```bash
   npm install
   ```
3. Inicie o servidor de desenvolvimento do Vite:
   ```bash
   npm run dev
   ```
   *A aplicação frontend subirá e estará acessível em: `http://localhost:5173`.*

---

## 5. Acesso e Credenciais de Demonstração

Para testar a plataforma, visualizar o Dashboard e realizar a gestão de tickets, acesse o frontend (`http://localhost:5173`) e utilize o seguinte usuário de testes que já estará cadastrado no banco:

* **E-mail:** `admin@email.com`
* **Senha:** `123456`
