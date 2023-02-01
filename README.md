#### Rest API Votos

<h2>Descrição da implementação</h2>
<p> Foi utilizado as separações dos domínios de Associados, Pauta, Sessão e Votos. Assim, poderá trabalhar independentemente </p>
<p> No caso, foi utilizado constraints para não repetir votos na mesma pauta para cada associado </p>
<p> Para isso, precisa criar um associado, uma pauta, uma sessão e, posteriormente votar. Ao fim das votações, precisa utilizar o /processes
para apurar os votos </p>
<p> Foi utilizado um Json na variável results da Sessão para que, se tiver mais tipos de votos como abstenção, nulo, será automatizamente inserida 
pela lógica da aplicação, só adicionar os votos no ENUM</p>
<p> Para contabilização da solução não perder os votos, foi utilizada as persistências </p>

<h2>O que foi utilizado:</h2>

<ul>Java 17</ul>
<ul>Spring Boot 3</ul>
<ul>Lombok</ul>
<ul>Mockito</ul>
<ul>Junit</ul>
<ul>Maven</ul>
<ul>JPA + Hibernate</ul>
<ul>MySQL</ul>
<ul>RabbitMQ</ul>

<h2>Rodando o Projeto</h2>

<p>O projeto está configurando com o tomcat embarcado. Não precisa necessidade de ter tomcat instalado. Somente o Java.</p>
<p>Na raiz do projeto, execute o comando ./mnvw clean package install. Isso irá gerar um build do projeto na pasta target</p>
<p>Depois, para iniciar o projeto, dentro da pasta target, utilize o comando: "java -jar assembliescorp-0.0.1-SNAPSHOT.jar"

<h5> Criar database </h5>
<p>Neste caso, vai precisar do MySql e RabbitMq Instalado e, caso precise, aponte variáveis de acordo com o aplication.yml</p>
<p> No caso, precisa criar uma base de dados no mysql do nome 'assembly' para poder usar o projeto </p>
<p> Poderá usar o Adminer http://192.168.0.11:8080/ para isto </p>

<h4> Docker Compose </h4>
<p>Com o docker instalado na máquina, execute o comando dentro da pasta do projeto:<p>
<p>utilize o docker-compose.yaml após gerar o pacote java. O apontamento do JAR está no ./target</p>

<p> Algumas coisas estão comentadas no docker-compose por conta de permissão.</p>

<p>A Base da API está na porta 8080</p>

http://localhost:8090/

<h2>Swagger - Documentação</h2>

<p>Documentação do swagger http://localhost:8080/swagger-ui/index.html</p>
<p>Recomendo utilizar o Postman ou Insomia para consumo de API.</p>
<p>Obs.: A versão do swagger no Spring 3 está com muita instabilidade quando estava desenvolvendo a api, recomendo abrir o docs e importar no insomina/postman http://localhost:8080/v3/api-docs</p>


<h2>Dozzle Logs Container</h2>

<p> O docker-compose conta com o Dozzle, projeto do Amir Raminfar (https://github.com/amir20/dozzle) caso precise utilizar para ver logs de containers </p>
<p> link: http://192.168.0.11:9999/ </p>

#### Tarefa 1

<p> Foi utilizado o RestTemplate (ainda que está mudando par ao WebClient), para validar a validação do CPF. Está comentado no código pois
no Heroku não estava funcionando no momento do projeto </p>

#### Tarefa 2

<p> Foi utilizad o RabbitMq já com a construção da Exchange com o bind de filas </p>

#### Tarefa 4
<p> Extratégia foi usada as boas práticas de api/v1 presentes nos resources </p>

## Futuras implementações
<ul>Criar o Exception Handlers para o SQL Constrains e o Validations</ul>
<ul>Implementar todos os Testes unitários</ul>


---

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
