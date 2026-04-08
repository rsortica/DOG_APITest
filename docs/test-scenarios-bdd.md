# Cenários de Teste em BDD

## Feature: Consultar raças de cachorros

### <a id="dog-001"></a>DOG-001: Listar todas as raças com sucesso
**Dado** que a Dog API está disponível  
**Quando** eu consultar a lista completa de raças  
**Então** a resposta deve ser `200`  
**E** o status do retorno deve ser `success`  
**E** o contrato JSON da lista de raças deve ser válido  
**E** a lista deve conter a raça esperada na massa de teste

### <a id="dog-002"></a>DOG-002: Listar sub-raças de uma raça válida
**Dado** que existe uma raça válida configurada na massa de teste  
**Quando** eu consultar as sub-raças dessa raça  
**Então** a resposta deve ser `200`  
**E** o status do retorno deve ser `success`  
**E** o contrato JSON da lista de sub-raças deve ser válido  
**E** a sub-raça esperada deve estar presente

### <a id="dog-003"></a>DOG-003: Consultar sub-raças de uma raça inválida
**Dado** que informo uma raça inexistente  
**Quando** eu consultar as sub-raças dessa raça  
**Então** a resposta deve ser `404`  
**E** o status do retorno deve ser `error`  
**E** o contrato JSON de erro deve ser válido  
**E** a mensagem deve indicar que a raça principal não existe

## Feature: Consultar imagens por raça

### <a id="dog-004"></a>DOG-004: Buscar imagem aleatória de uma raça válida
**Dado** que existe uma raça válida configurada na massa de teste  
**Quando** eu solicitar uma imagem aleatória dessa raça  
**Então** a resposta deve ser `200`  
**E** o status do retorno deve ser `success`  
**E** o contrato JSON da imagem aleatória deve ser válido  
**E** a URL retornada não deve estar vazia  
**E** a URL deve conter o segmento esperado da raça

### <a id="dog-005"></a>DOG-005: Listar imagens de raças válidas
**Dado** que existe uma raça válida `<breed>`  
**Quando** eu listar as imagens dessa raça  
**Então** a resposta deve ser `200`  
**E** o status do retorno deve ser `success`  
**E** o contrato JSON da lista de imagens deve ser válido  
**E** a lista deve conter ao menos uma imagem  
**E** a primeira imagem deve conter `<expectedPathSegment>`

Exemplos:

| breed | expectedPathSegment |
| --- | --- |
| hound | /breeds/hound |
| husky | /breeds/husky |

### <a id="dog-006"></a>DOG-006: Buscar imagem aleatória de uma raça inválida
**Dado** que informo uma raça inexistente  
**Quando** eu solicitar uma imagem aleatória dessa raça  
**Então** a resposta deve ser `404`  
**E** o status do retorno deve ser `error`  
**E** o contrato JSON de erro deve ser válido  
**E** a mensagem deve indicar que a raça principal não existe

### <a id="dog-007"></a>DOG-007: Listar imagens de uma raça inválida
**Dado** que informo uma raça inexistente  
**Quando** eu listar as imagens dessa raça  
**Então** a resposta deve ser `404`  
**E** o status do retorno deve ser `error`  
**E** o contrato JSON de erro deve ser válido  
**E** a mensagem deve indicar que a raça principal não existe

## Feature: Consultar imagens por sub-raça

### <a id="dog-008"></a>DOG-008: Buscar imagem aleatória de sub-raças válidas
**Dado** que existe a raça `<breed>` com a sub-raça `<subBreed>`  
**Quando** eu solicitar uma imagem aleatória dessa sub-raça  
**Então** a resposta deve ser `200`  
**E** o status do retorno deve ser `success`  
**E** o contrato JSON da imagem aleatória deve ser válido  
**E** a URL retornada deve conter `<expectedPathSegment>`

Exemplos:

| breed | subBreed | expectedPathSegment |
| --- | --- | --- |
| hound | afghan | /hound-afghan |
| bulldog | boston | /bulldog-boston |

### <a id="dog-009"></a>DOG-009: Listar imagens de sub-raças válidas
**Dado** que existe a raça `<breed>` com a sub-raça `<subBreed>`  
**Quando** eu listar as imagens dessa sub-raça  
**Então** a resposta deve ser `200`  
**E** o status do retorno deve ser `success`  
**E** o contrato JSON da lista de imagens deve ser válido  
**E** a lista deve conter ao menos uma imagem  
**E** a primeira imagem deve conter `<expectedPathSegment>`

Exemplos:

| breed | subBreed | expectedPathSegment |
| --- | --- | --- |
| hound | afghan | /hound-afghan |
| bulldog | boston | /bulldog-boston |

### <a id="dog-010"></a>DOG-010: Buscar imagem aleatória de uma sub-raça inválida
**Dado** que informo uma sub-raça inexistente para uma raça válida  
**Quando** eu solicitar uma imagem aleatória dessa sub-raça  
**Então** a resposta deve ser `404`  
**E** o status do retorno deve ser `error`  
**E** o contrato JSON de erro deve ser válido  
**E** a mensagem deve indicar que a sub-raça não existe

### <a id="dog-011"></a>DOG-011: Listar imagens de uma sub-raça inválida
**Dado** que informo uma sub-raça inexistente para uma raça válida  
**Quando** eu listar as imagens dessa sub-raça  
**Então** a resposta deve ser `404`  
**E** o status do retorno deve ser `error`  
**E** o contrato JSON de erro deve ser válido  
**E** a mensagem deve indicar que a sub-raça não existe
