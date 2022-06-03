### Pré requisitos

Para executar o projeto, será necessário instalar os seguintes programas:

- [JDK 1.8: Necessário para executar o projeto Java](https://www.oracle.com/java/technologies/downloads/#java8)
- [Maven 3.5.3: Necessário para realizar o build do projeto Java](http://mirror.nbtelecom.com.br/apache/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.zip)
- IDE de sua preferência: Para desenvolvimento do projeto


### Desenvolvimento

Para iniciar o desenvolvimento, é necessário clonar o projeto do GitHub num diretório de sua preferência:

```shell
cd "diretorio de sua preferencia"
git clone https://github.com/marcellcoelho/desafio-back-votos.git
```

É preciso adicionar os paramentros no application.properties:

#Cloud AWS (Me solicita)
```shell
cloud.aws.credentials.access-key=--
cloud.aws.credentials.secret-key=--
```

#PostgreSQL (Base estará online até o dia 10/06/2022) (Me solicita)
```shell
spring.datasource.url=jdbc:postgresql://br-com-southsystem-1.cl5np2shlq1g.sa-east-1.rds.amazonaws.com:5432/assembleia
spring.datasource.username=--
spring.datasource.password=--
spring.jpa.hibernate.ddl-auto=update
```

### Construção

Para construir o projeto com o Maven, executar os comando abaixo:

```shell
mvn clean install
```

O comando irá baixar todas as dependências do projeto e criar um diretório *target* com os artefatos construídos, que incluem o arquivo jar do projeto. Além disso, serão executados os testes unitários, e se algum falhar, o Maven exibirá essa informação no console.


### Testes

Para rodar os testes, utilize o comando abaixo:

```
mvn test
```


### Links

Para acesso fácil:

- [Swagger](http://localhost:8080/swagger-ui.html)