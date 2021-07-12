# API Votação - Backend

API desenvolvida para apresentar como solução prova técnica.

## Iniciando

### Pré-requisitos

* Java versão 11
* Maven 
* Docker / Docker Compose

### Instalando

Baixe o projeto para um diretório local, e execute os seguintes comandos abaixo:

1. Empacotando o projeto 
```
mvn clean package
```

2. Executando build de containers
```
docker-compose build
```

3. Executando containers atráves do docker-compose
```
docker-compose up -d
```
Pronto!

### Documentação da API

* Swagger(http://localhost:8080/swagger-ui/)

![Alt text](docs/swagger-ui.png)

### Kafdrop

* Dashboard(http://localhost:19000/)

![Alt text](docs/kafdrop.png)

* Topic

![Alt text](docs/kafdrop-topic.png)

* Message

![Alt text](docs/kafdrop-message.png)


