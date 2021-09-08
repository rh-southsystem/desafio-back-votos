# Solução do Desafio Técnico
## O que foi utilizado

- Docker
- Docker-compose
- Java 11
- Spring-boot 2.5.4
- Postgres 11
- Kafka
- Zookeeper
- Git

## Pré-requisitos
- Docker
- Docker-compose
- Java 11

## Estrutura do sistema
O sistema foi estruturado pelos pacotes:
- Config - Onde se encontra a classe SwaggerConfig.java para configuração do swagger para a documentação da api.

- Domain - Onde se encontra todas as entidades do projeto e a partir delas a criação do banco de dados.

- Exception - As exceções criadas para o sistema.

- Repository - Os repositórios do domínio da aplicação.

- Services - Ficaram as interfaces dos serviços, os pacotes das classes DTOs, o pacote para as implementações das interfaces dos serviços, o pacote  dos mappers para mapeamento das classes de entidades para os DTOs e o pacote Util.

- Web.rest.v1 - Pacote onde ficou todos os controladores da api, a qual foi versionada com a utilização do pacaote v1.

- Test - Onde ficaram as classes de testes de unidade do sistema.

## Bibliotecas e tecnologias utilizadas
- Lombok para diminuir a verbosidade.
- Postgresql para o banco de dados.
- H2 para o banco de dados de teste para o perfil test.
- Junit para o desenvolvimento dos testes de unidade.
- Swagger2 para a documentação da api
- Spring-boot-starter-webflux para acesso da api externa para checagem de CPF
- Kafka e Zookeeper para o desenvolvemento da tarefa de mensageria.

## Clone e Preparação do Ambiente
O serviço do docker precisa ter sido iniciado antes da execução do docker-compose

    $ git clone https://github.com/viniciuscst/desafio-back-votos.git
    $ cd desafio-back-votos
    $ docker-compose up -d

## Build e execução do sistema
    $ ./mvnw install
    $ ./mvnw spring-boot:run
## Execução dos testes
    $ ./mvnw test
## Acesso a api do sistema
A versão 1 da api pode ser acessada em http://localhost:8080/api/v1
## Acesso a documentação swagger
Acesso a documentação swagger http://localhost:8080/api/swagger-ui.html
# Desafio Técnico
## Objetivo
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação. Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:
- Cadastrar uma nova pauta;
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta);
- Contabilizar os votos e dar o resultado da votação na pauta.

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que não infrinja direitos de uso).

É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

### Tarefas bônus
As tarefas bônus não são obrigatórias, mas nos permitem avaliar outros conhecimentos que você possa ter.

A gente sempre sugere que o candidato pondere e apresente até onde consegue fazer, considerando o seu
nível de conhecimento e a qualidade da entrega.
#### Tarefa Bônus 1 - Integração com sistemas externos
Integrar com um sistema que verifique, a partir do CPF do associado, se ele pode votar
- GET https://user-info.herokuapp.com/users/{cpf}
- Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos;
- Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação
Exemplos de retorno do serviço

#### Tarefa Bônus 2 - Mensageria e filas
Classificação da informação: Uso Interno
O resultado da votação precisa ser informado para o restante da plataforma, isso deve ser feito preferencialmente através de mensageria. Quando a sessão de votação fechar, poste uma mensagem com o resultado da votação.

#### Tarefa Bônus 3 - Performance
Imagine que sua aplicação possa ser usada em cenários que existam centenas de milhares de votos. Ela deve se comportar de maneira performática nesses cenários;
- Testes de performance são uma boa maneira de garantir e observar como sua aplicação se comporta.

#### Tarefa Bônus 4 - Versionamento da API
Como você versionaria a API da sua aplicação? Que estratégia usar?

### O que será analisado
- Simplicidade no design da solução (evitar over engineering)
- Organização do código
- Arquitetura do projeto
- Boas práticas de programação (manutenibilidade, legibilidade etc)
- Possíveis bugs
- Tratamento de erros e exceções
- Explicação breve do porquê das escolhas tomadas durante o desenvolvimento da solução
- Uso de testes automatizados e ferramentas de qualidade
- Limpeza do código
- Documentação do código e da API
- Logs da aplicação
- Mensagens e organização dos commits

### Observações importantes
- Não inicie o teste sem sanar todas as dúvidas
- Iremos executar a aplicação para testá-la, cuide com qualquer dependência externa e deixe claro caso haja instruções especiais para execução do mesmo
- Teste bem sua solução, evite bugs
