## Requirements

For building and running the application you need:

- [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [postgresql](https://www.postgresql.org/download/)

## Running the application locally

It is necessary to create a database in postgres after installation with the name: dbvoting.

After database creation, run the command, in root folder:

$ mvn install

Open in root folder power sheel or cmd
and insert command:

$ java -jar .\target\voting-0.0.1-SNAPSHOT.jar

## How to use the application

The application will be listening by default on HTTP port 8080.

## End Points

Some calls to this api are as an example in this postman link below:

https://www.getpostman.com/collections/7482b99b1e8f6592d3ba

Associates API

Create associate:

POST : localhost:8080/api/voting/associates
{
   "name": "teste 1",
   "document": "03122324032" 
}

------------

Topic API

Create Topic

POST: localhost:8080/api/voting/topics
{
  "name" : "TOPICO TESTE 1",
  "description" : "DESCRICAO TOPICO TESTE 1"
}

Result Topic 
GET: localhost:8080/api/voting/topics/{idTopic}/ascertainment

Ex Response : 
{
    "countVotes": 2,
    "countVotesYes": 1,
    "countVotesNo": 1,
    "topicEntity": {
        "id": 1,
        "name": "TOPICO TESTE 1",
        "description": "DESCRICAO TOPICO TESTE 1"
    }
}
------------

Session API 

Create Session 

POST: localhost:8080/api/voting/sessions

{
   "idTopic": 1,
   "expiration": null
}

OR

{
   "idTopic": 1,
   "expiration": "2021-06-24 23:38:28"
}

----------------------------------

Vote API

Create vote

POST: localhost:8080/api/voting/votes
{
    "voteEnum" : "SIM",
    "idSession" : 1,
    "idAssociate" : 1
}

----------------------------------


















