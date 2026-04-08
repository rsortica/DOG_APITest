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
|-- clients      -> camada de acesso à API
|-- models       -> mapeamento das respostas
|-- specs        -> request/response specs reutilizáveis
|-- tests        -> cenários de teste por domínio
`-- utils        -> configuração e helpers

src/test/resources
|-- config       -> propriedades por ambiente
|-- schemas      -> contratos JSON das respostas
`-- testdata     -> massas de teste externas
```

## Ambientes

O ambiente é controlado por `-Denv`:

```bash
mvn test -Denv=dev
mvn test -Denv=hml
mvn test -Denv=prod
```

Atualmente todos apontam para `https://dog.ceo`, mas a estrutura já está preparada para separar URLs e demais configurações.

## Perfis de Execução

- suíte completa: `mvn clean test`
- smoke: `mvn clean test -Psmoke`
- regression: `mvn clean test -Pregression`

Os testes `smoke` cobrem o fluxo principal. Os testes `regression` incluem toda a suíte, inclusive contratos negativos e cenários parametrizados.

## Timeouts e Performance

Os requests usam timeouts configuráveis por ambiente:

- `timeout.connection.ms`
- `timeout.socket.ms`
- `timeout.response.ms`

Além disso, as respostas de sucesso validam um tempo máximo configurável via `timeout.response.ms`.

## Testes Implementados

- Listagem de todas as raças com validação de JSON Schema
- Listagem de sub-raças com dados externos
- Busca de imagem aleatória por raça
- Listagem de imagens por raça
- Busca de imagem aleatória por sub-raça
- Listagem de imagens por sub-raça
- Cobertura negativa para raça e sub-raça inválidas

## Catálogo de Cenários

- `DOG-001` [Listar todas as raças com sucesso](docs/test-scenarios-bdd.md#dog-001)
- `DOG-002` [Listar sub-raças de uma raça válida](docs/test-scenarios-bdd.md#dog-002)
- `DOG-003` [Consultar sub-raças de uma raça inválida](docs/test-scenarios-bdd.md#dog-003)
- `DOG-004` [Buscar imagem aleatória de uma raça válida](docs/test-scenarios-bdd.md#dog-004)
- `DOG-005` [Listar imagens de raças válidas](docs/test-scenarios-bdd.md#dog-005)
- `DOG-006` [Buscar imagem aleatória de uma raça inválida](docs/test-scenarios-bdd.md#dog-006)
- `DOG-007` [Listar imagens de uma raça inválida](docs/test-scenarios-bdd.md#dog-007)
- `DOG-008` [Buscar imagem aleatória de sub-raças válidas](docs/test-scenarios-bdd.md#dog-008)
- `DOG-009` [Listar imagens de sub-raças válidas](docs/test-scenarios-bdd.md#dog-009)
- `DOG-010` [Buscar imagem aleatória de uma sub-raça inválida](docs/test-scenarios-bdd.md#dog-010)
- `DOG-011` [Listar imagens de uma sub-raça inválida](docs/test-scenarios-bdd.md#dog-011)

## Executar

```bash
mvn clean test
mvn clean test -Psmoke
mvn clean test -Pregression
```

## Dados Externos e Schemas

- Massas de teste: `src/test/resources/testdata/dog-api-data.json`
- Schemas JSON: `src/test/resources/schemas`
- Cenários em BDD: `docs/test-scenarios-bdd.md`

## CI/CD e Allure

O projeto inclui um workflow do GitHub Actions em `.github/workflows/api-tests.yml` que:

- executa `mvn -B clean test -Denv=dev`
- gera o relatório HTML do Allure com `mvn -B allure:report`
- publica `target/surefire-reports` como artefato
- publica `target/allure-results` como artefato
- publica `target/site/allure-maven-plugin` como artefato HTML
- faz deploy do relatório no GitHub Pages quando houver push para `main`
