# DOG API Test Automation Framework

Framework base para testes de API com Java, Maven e Rest Assured usando a API pública `https://dog.ceo/dog-api/documentation`.

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
`-- config       -> propriedades por ambiente
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

- Listagem de todas as racas
- Listagem de sub-racas de `hound`
- Busca de imagem aleatoria por raca

## Executar

```bash
mvn clean test
```

## Proximos passos sugeridos

- Adicionar JSON Schema validation
- Externalizar dados de teste
- Incluir execucao paralela
- Integrar com pipeline CI/CD
