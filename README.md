# 🛒 Sistema de Gestão de Pedidos (E-commerce API)

API REST desenvolvida com **Spring Boot** que simula o backend de um sistema de e-commerce, aplicando arquitetura em camadas, modelagem relacional com JPA/Hibernate e regras de negócio reais.

---

## 🎯 Objetivo

Desenvolver uma API REST aplicando:

* Programação Orientada a Objetos (POO)
* Arquitetura em 3 camadas (Controller → Service → Repository)
* Modelagem relacional com JPA/Hibernate
* Regras de negócio consistentes
* Boas práticas de desenvolvimento backend

---

## 🧩 Contexto do Sistema

O sistema gerencia:

* 👤 Clientes
* 📍 Endereços
* 📦 Produtos
* 🧾 Pedidos
* 🧮 Itens do Pedido

---

## 🏗️ Arquitetura

O projeto segue o padrão:

```
Controller → Service → Repository
```

### 📌 Responsabilidades

* **Controller**

  * Recebe requisições HTTP
  * Retorna respostas da API
  * Não contém regra de negócio

* **Service**

  * Contém regras de negócio
  * Valida dados
  * Orquestra operações

* **Repository**

  * Acesso ao banco de dados
  * Interface com JPA/Hibernate

---

## 🗂️ Modelagem de Domínio

### 🧑 Cliente

* id
* nome
* email

Relacionamentos:

* 1:N com Endereço
* 1:N com Pedido

---

### 📍 Endereço

* id
* rua
* cidade
* cep

Relacionamento:

* N:1 com Cliente

---

### 📦 Produto

* id
* nome
* preco
* estoque

---

### 🧾 Pedido

* id
* data
* status (CRIADO, PAGO, ENVIADO, CANCELADO)
* total

Relacionamentos:

* N:1 com Cliente
* 1:1 com Endereço
* 1:N com ItemPedido

---

### 🧮 ItemPedido (Entidade Associativa)

* id
* quantidade
* precoUnitario

Relacionamentos:

* N:1 com Pedido
* N:1 com Produto

---

## 🔗 Resumo dos Relacionamentos

* Cliente → Endereço (1:N)
* Cliente → Pedido (1:N)
* Pedido → Endereço (1:1)
* Pedido → ItemPedido (1:N)
* Produto → ItemPedido (1:N)

---

## ⚙️ Regras de Negócio

### ✔️ Email único

* Não permite clientes com emails duplicados

### ✔️ Controle de estoque

* Valida disponibilidade antes de criar pedido
* Subtrai automaticamente do estoque

### ✔️ Cálculo do total

* Não pode ser enviado pelo cliente
* Calculado internamente:

```
total = soma(quantidade * precoUnitario)
```

---

### ✔️ Cancelamento de pedido

* Apenas pedidos com status `CRIADO` podem ser cancelados

---

### ✔️ Fluxo de status

Fluxo permitido:

```
CRIADO → PAGO → ENVIADO
CRIADO → CANCELADO
```

Restrições:

* Não pode pular etapas
* Não pode voltar status

---

## 🚀 Endpoints da API

### 👤 Cliente

| Método | Endpoint       | Descrição         |
| ------ | -------------- | ----------------- |
| POST   | /clientes      | Cadastrar cliente |
| GET    | /clientes      | Listar clientes   |
| GET    | /clientes/{id} | Buscar cliente    |
| PUT    | /clientes/{id} | Atualizar cliente |
| DELETE | /clientes/{id} | Remover cliente   |

---

### 📦 Produto (CRUD)

| Método | Endpoint       |
| ------ | -------------- |
| POST   | /produtos      |
| GET    | /produtos      |
| GET    | /produtos/{id} |
| PUT    | /produtos/{id} |
| DELETE | /produtos/{id} |

---

### 🧾 Pedido

| Método | Endpoint             | Descrição        |
| ------ | -------------------- | ---------------- |
| POST   | /pedidos             | Criar pedido     |
| GET    | /pedidos             | Listar pedidos   |
| GET    | /pedidos/{id}        | Buscar pedido    |
| PATCH  | /pedidos/{id}/status | Atualizar status |
| DELETE | /pedidos/{id}        | Cancelar pedido  |

---

## 🛠️ Tecnologias Utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven
* Banco de dados relacional

---

## 🗃️ Persistência

Uso obrigatório de:

* JPA
* Hibernate

Principais anotações utilizadas:

* `@Entity`
* `@Id`
* `@GeneratedValue`
* `@OneToMany`
* `@ManyToOne`
* `@OneToOne`

Configurações aplicadas:

* Cascade
* Fetch (LAZY)

---

## 📦 Estrutura do Projeto

```
controller/
service/
repository/
entity/
dto/ (opcional)
exception/
```

---

## 🧪 Como Executar

### 1. Clonar o projeto

```
git clone <URL_DO_REPOSITORIO>
```

---

### 2. Executar aplicação

```
mvn spring-boot:run
```

---

### 3. Acessar API

```
http://localhost:8080
```

---

## 🧪 Testes

Ferramentas recomendadas:

* Postman
* Insomnia

---

## 💡 Boas Práticas Aplicadas

* Separação de responsabilidades
* Baixo acoplamento
* Alta coesão
* Regras de negócio centralizadas no Service
* Validações de integridade
* Métodos claros e objetivos

---

## 🚀 Diferenciais do Projeto

* Validação de vínculo entre cliente e endereço
* Controle de estoque automatizado
* Cálculo interno de total do pedido
* Uso de entidade associativa (ItemPedido)
* Fluxo de status com restrições reais
* API REST bem estruturada

---

## 🏆 Conclusão

Este projeto demonstra a construção de uma API REST completa, com foco em regras de negócio reais, modelagem consistente e boas práticas de desenvolvimento backend.

---

## 👨‍💻 Autor

Desenvolvido por **Pabline Pereira**
