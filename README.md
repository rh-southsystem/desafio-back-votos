# Sistema de votação
## Sobre
Sistema de votação desenvolvido em Spring Boot.
O aplicativo inclui dois CRUDs para associados e assembleias, nas quais esses associados irão votar.
Com uma assembleia criada, o usuário pode começar a votação em /api/assembly/startVoting/{id}.
A duração da assembleia é definida por um long correspondente aos milissegundos, cujo valor mínimo é de 60000 (um minuto).
Para votar, é necessário um usuário cadastrado com CPF válido e uma assembleia em andamento através do endpoint /api/vote

## Instruções
Instalar o postgres com senha root e criar um banco chamado southsystem.