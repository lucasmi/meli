# meli

## Backend: API Development

### API Endpoints

- Implement a RESTful API that supports the frontend by providing the necessary product details ✅
- The primary endpoint should fetch product details ✅
- You can use any backend technology or framework of your choice ✅
- Do not use real databases, persist everything in local JSON or CSV files ✅

### Non-functional requirements

- Proper error handling ✅
- Documented ✅ (Swagger)
- At least 80% code coverage ✅

## Documentation

Along with your code submission, include a brief (1-2 page) document that:

- Explains your design choices. ✅ (README.md)
- Describes any challenges you faced and how you addressed them. ✅ (README.md)

## Submission

- Provide a link to your repository or a zipped project folder. ✅  
  [https://github.com/lucasmi/meli](https://github.com/lucasmi/meli)
- It must contain a `run.md` explaining how to run the project ✅  
  [https://github.com/lucasmi/meli/run.md](https://github.com/lucasmi/meli/run.md)


  ## Explains your design choices  
  Com base nas tecnologias sob meu domínio, optei por utilizar Java, considerando meu maior tempo de experiência com essa linguagem. Uma vez definida a tecnologia principal, passei a analisar os requisitos do projeto.

  Como precisava desenvolver endpoints baseados na arquitetura REST, escolhi o Spring Boot pela agilidade no desenvolvimento e pela facilidade de integração. Para a camada de persistência, a única restrição era utilizar arquivos JSON ou CSV. Diante disso, optei pelo formato JSON, por ser mais flexível e adequado à estrutura dos dados que pretendo manipular.

  Comecei a idealizar diferentes abordagens para a construção da solução, ciente dos desafios que enfrentaria com o paralelismo na manipulação de arquivos e com o relacionamento entre dados. Foi então que surgiu a ideia de me inspirar no JPA — uma biblioteca amplamente utilizada para abstrair operações com bancos de dados relacionais.

  Adaptei essa inspiração ao contexto de arquivos: estruturei os dados em pastas representando "tabelas" e considerei que cada arquivo individual simbolizaria uma "linha" nessa estrutura. Para simplificar os relacionamentos e o controle de chaves primárias, optei pelo uso de UUIDs como identificadores únicos. Dessa forma, evitei a complexidade de gerenciar identificadores sequenciais manualmente, o que também trouxe maior robustez e escalabilidade ao projeto.

  Diagrama de relacionamento
    ![Diagrama](https://github.com/lucasmi/meli/blob/main/diagrama.png)

  ## Challenges
  - Manipulação de arquivos com threads paralelas: Para garantir a integridade dos dados durante operações concorrentes, utilizei mecanismos de lock nas threads antes da manipulação dos arquivos. Isso assegurou que apenas uma thread tivesse acesso ao recurso compartilhado por vez, evitando condições de corrida.

  - Relacionamento e manipulação dos arquivos gerados: Para facilitar e padronizar o desenvolvimento, criei a biblioteca JpaJson, inspirada nos princípios do JPA. Essa abordagem me levou a estruturar o código em duas camadas principais:
    - A camada Repository, responsável pela persistência dos dados no formato JSON.
    - A camada Entity, encarregada da representação e manipulação dos dados.