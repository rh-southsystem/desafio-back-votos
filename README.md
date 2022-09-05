# Desafio Técnico
## Objetivo
No cooperativismo, cada associatePost possui um voto e as decisões são tomadas em assembleias, por votação. A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação. Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:
- Cadastrar uma nova voteSession;
- Abrir uma sessão de votação em uma voteSession (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associatePost é identificado por um id único e pode votar apenas uma vez por voteSession);
- Contabilizar os votos e dar o resultado da votação na voteSession.

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que não infrinja direitos de uso).

É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

### Tarefas bônus
As tarefas bônus não são obrigatórias, mas nos permitem avaliar outros conhecimentos que você possa ter.

A gente sempre sugere que o candidato pondere e apresente até onde consegue fazer, considerando o seu
nível de conhecimento e a qualidade da entrega.
#### Tarefa Bônus 1 - Integração com sistemas externos
Integrar com um sistema que verifique, a partir do CPF do associatePost, se ele pode votar
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

# Instruções de Uso
### Pré requisito
Para executar este aplicativo, você precisa instalar duas ferramentas: Docker e Docker Compose.

Instruções de como instalar o Docker no [Ubuntu](https://docs.docker.com/engine/install/ubuntu/), [Windows](https://docs.docker.com/docker-for-windows/install/), [Mac](https://docs.docker.com/docker-for-mac/install/).

Docker Compose já está incluído nos pacotes de instalação para Windows e Mac, portanto, apenas usuários do Ubuntu precisam seguir estas [instruções](https://docs.docker.com/compose/install/).

### Arquitetura
[imagens]
### Executando
Pode ser executado um único comando no terminal:
~~~
$ docker-compose up -d
~~~
Se você quiser pará-lo, execute o seguinte comando:
~~~
$ docker-compose down
~~~

Irá sube três instâncias: Aplicalção, PgAdmin e o Postgres. 

O PgAdmin é uma ferramenta para gerenciar conteúdo em bancos de dados PostgreSQL.
- Para acessar o portal do PgAdmin, utilize o link com as credencias abaixo:
  - URL: http://localhost:9080/
  - Usuario: xpdo@mail.com.br
  - Senha: root
- Para a conexão com o banco de dados PostgreSQL, utilize as credenciais no arquivo .env ou os dados abaixo:
  - Hostname: postgre-sql.cloud.local
  - Port: 5432
  - Usuario: postgres
  - Senha: Postgres2022!
A Aplicação (back-votos) você encontrar a lista completa de endpoints REST disponíveis pode ser encontrada na IU Swagger, que esta disponivel no link: http://localhost:8080/webjars/swagger-ui/index.html

### Massa de dados
Associate:

| id  | Name                              | CPF          |
|-----|-----------------------------------|--------------|
| 1   | Emanuel Hugo Augusto Aragão       | 96722007146  |
| 2   | Pedro Gabriel Fernando Barros     | 82955016900  |
| 3   | Joaquim Márcio Fábio Rodrigues    | 97638987730  |
| 4   | Stefany Agatha Kamilly Santos     | 91864586397  |
| 5   | Alessandra Raquel Isis de Paula   | 09257975452  |
| 6   | Analu Liz Stella dos Santos       | 19887089745  |
| 7   | Vitor César Raul Novaes           | 05340638103  |
| 8   | Manuel Luís Tomás Rodrigues       | 98096435949' |

Vote Session

| id  | Title                                                  | Date Start          | Date End            |
|-----|--------------------------------------------------------|---------------------|---------------------|
| 1   | Instalação dos quebra-molas nas vias públicas          | 2022-02-10 07:00:00 | 2022-02-15 20:00:00 |
| 2   | Proibição som automotivo nas areas de parque infantil  | 2022-06-02 07:00:00 | 2022-06-02 19:00:00 |
| 3   | Permitir campanha/publicidade politica na area pública | 2022-09-02 12:00:00 | 2022-09-10 12:00:00 |