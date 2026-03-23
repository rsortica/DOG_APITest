# API Test Automation Framework

## 📌 Overview

Este projeto tem como objetivo implementar um framework de automação de testes de API utilizando **Java**, **Maven** e **Rest Assured**, seguindo padrões utilizados em ambientes corporativos.

A abordagem adotada prioriza:

* Escalabilidade
* Manutenibilidade
* Separação de responsabilidades
* Facilidade de integração com pipelines de CI/CD

---

## 🧱 Tech Stack

* Java 11+
* Maven
* Rest Assured
* JUnit 5
* Hamcrest

---

## 🏗️ Arquitetura do Projeto

A estrutura segue um padrão inspirado em boas práticas de mercado, semelhante ao Page Object Model adaptado para APIs:

```
src/test/java
 ├── base         → Configurações globais (setup, specs)
 ├── clients      → Camada de acesso às APIs
 ├── models       → Representação dos objetos (POJOs)
 ├── builders     → Construção de payloads
 ├── specs        → Configurações reutilizáveis (request/response)
 ├── tests        → Casos de teste
 └── utils        → Utilitários (config, helpers, etc)
```

---

## 🎯 Objetivo do Framework

* Padronizar testes de API
* Facilitar manutenção e evolução
* Permitir execução em múltiplos ambientes (dev, hml, prod)
* Integrar facilmente com pipelines CI/CD
* Reduzir duplicação de código

---

## 🔁 Fluxo de Teste

1. O teste define o cenário
2. O client executa a chamada HTTP
3. O builder cria o payload (quando necessário)
4. O model representa os dados
5. O response é validado no teste

---

## ⚙️ Configuração de Ambiente

Os testes podem ser executados em diferentes ambientes utilizando propriedades do Maven:

```bash
mvn test -Denv=dev
mvn test -Denv=hml
mvn test -Denv=prod
```

---

## 🧪 Padrão de Escrita de Testes

Os testes devem seguir princípios claros:

* Independência entre testes
* Dados dinâmicos sempre que possível
* Nome descritivo (comportamento esperado)
* Foco em validação, não em implementação

Exemplo:

```
deveCriarUsuarioComSucesso
deveRetornarErroAoCriarUsuarioSemEmail
```

---

## 📏 Boas Práticas Adotadas

* Separação de responsabilidades (Test vs Client)
* Reutilização de configurações (Request/Response Specs)
* Uso de builders para criação de dados
* Evitar dados fixos (hardcoded)
* Logging para debugging
* Estrutura preparada para paralelismo

---

## 🚀 CI/CD

O projeto foi estruturado para fácil integração com ferramentas de CI/CD como:

* Jenkins
* GitHub Actions
* GitLab CI

Execução padrão:

```bash
mvn clean test
```

---

## 📈 Evolução do Projeto

Este framework será evoluído incrementalmente com:

* Autenticação (JWT/OAuth2)
* Testes de contrato (JSON Schema)
* Relatórios (Allure)
* Execução paralela
* Integração com banco de dados
* Mock de APIs

---

## 📚 Estratégia de Aprendizado

Este projeto está sendo construído de forma incremental, simulando um ambiente real de empresa.

Cada etapa adiciona:

* Nova camada de abstração
* Boas práticas aplicadas
* Evolução da arquitetura

---

## 🧩 Pré-requisitos

* Java instalado e configurado
* Maven instalado
* IDE (VsCode recomendado)

---

## ▶️ Primeira Execução

```bash
mvn clean test
```

---

## 📌 Observações

* O projeto não depende de execução ordenada de testes
* Cada teste deve ser executável isoladamente
* A estrutura favorece crescimento contínuo

---
