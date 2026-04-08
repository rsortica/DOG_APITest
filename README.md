# DOG API Test Automation Framework

Framework base para testes de API com Java, Maven e Rest Assured usando a API publica `https://dog.ceo/dog-api/documentation`.

## Stack

- Java 11+
- Maven
- Rest Assured
- JUnit 5
- Hamcrest
- Allure

## Estrutura

```text
src/test/java
|-- base         -> setup global do Rest Assured
|-- clients      -> camada de acesso a API
|-- models       -> mapeamento das respostas
|-- specs        -> request/response specs reutilizaveis
|-- tests        -> cenarios de teste
`-- utils        -> configuracao e helpers

src/test/resources
|-- config       -> propriedades por ambiente
|-- schemas      -> contratos JSON das respostas
`-- testdata     -> massas de teste externas
```

## Ambientes

O ambiente e controlado por `-Denv`:

```bash
mvn test -Denv=dev
mvn test -Denv=hml
mvn test -Denv=prod
```

Atualmente todos apontam para `https://dog.ceo`, mas a estrutura ja esta preparada para separar URLs e demais configuracoes.

## Testes implementados

- Listagem de todas as racas com validacao de JSON Schema
- Listagem de sub-racas com dados externos
- Busca de imagem aleatoria por raca

## Executar

```bash
mvn clean test
```

## Dados externos e schemas

- Massas de teste: `src/test/resources/testdata/dog-api-data.json`
- Schemas JSON: `src/test/resources/schemas`

## CI/CD

O projeto inclui um workflow do GitHub Actions em `.github/workflows/api-tests.yml` que:

- executa `mvn -B clean test -Denv=dev`
- publica `target/surefire-reports` como artefato
- publica `allure-results` como artefato

## Proximos passos sugeridos

- Incluir execucao paralela
- Expandir a cobertura de endpoints
- Publicar relatorios Allure
