# Spring Boot Lab - Logging HTTP Starter

Ce projet contient un starter Spring Boot reutilisable pour le logging HTTP et une application de demonstration qui l'integre.

## Contenu du depot
- [spring-boot-starter-http-logger](./spring-boot-starter-http-logger): starter de logging HTTP
- [spring-boot-lab-app](./spring-boot-lab-app): application de démonstration qui utilise le starter

## Pre-requis
- Java 17+
- Maven 3.9+
- Spring Boot 3.x ou 4.x

## Construction
Pour installer le starter sur le repository local :

```bash
mvn -f spring-boot-starter-http-logger clean install
```

## Utilisation du starter

### Ajouter la dependance
Dans le `pom.xml` du projet de démonstration, le starter est ajouté via la dépendance suivante :

```xml
<dependency>
    <groupId>io.github.giannialberico</groupId>
    <artifactId>spring-boot-starter-http-logger</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Configurer les proprietes
Dans `application.properties` (ou `application.yml`) :

```properties
http.logger.enabled=true
http.logger.include-request-body=true
http.logger.include-response-body=true
```

### Proprietes disponibles
- `http.logger.enabled` : active/désactive les logs HTTP
- `http.logger.include-request-body` : inclut le corps de la requête dans les logs
- `http.logger.include-response-body` : inclut le corps de la réponse dans les logs
- `http.logger.max-body-size` : limite la taille des bodies dans les logs (ex: `1KB`, `2KB`)

## Application de demonstration

### Lancer l'application

```bash
mvn -f spring-boot-lab-app spring-boot:run
```

### Endpoints
- `GET /api/hello` : retourne un message simple
- `POST /api/echo` : renvoie le payload passé dans le corps de la requête
- `GET /api/slow` : simule un traitement de 3 secondes

### Exemple d'appels

```bash
curl -i http://localhost:8080/api/hello
curl -i -H "X-Correlation-ID: demo-123" http://localhost:8080/api/hello
curl -i -X POST http://localhost:8080/api/echo -d "ping"
curl -i http://localhost:8080/api/slow
```

## Logs générés

Le starter journalise :
- Requêtes entrantes : méthode, URI, en-têtes utiles, corps (optionnel)
- Réponses sortantes : status, en-têtes, corps (optionnel), taille
- Temps d'exécution : horodatage et durée totale en ms
- Correlation ID : lu depuis `X-Correlation-ID` ou généré automatiquement, puis ajouté au MDC

Exemple de logs :

```
2026-03-13 20:14:15 INFO  [http-nio-8080-exec-9] my-correlation-id i.g.giannialberico.HttpLoggingFilter - 
---------- INCOMING HTTP REQUEST ----------
Date     : Fri Mar 13 20:14:15 CET 2026
Method   : POST
URI      : /api/echo
Headers  : Content-Type: application/json; Accept: */*; X-Correlation-ID: my-correlation-id; User-Agent: PostmanRuntime/7.51.1; 

2026-03-13 20:14:15 INFO  [http-nio-8080-exec-9] my-correlation-id i.g.giannialberico.HttpLoggingFilter - 
---------- HTTP REQUEST ----------
Date     : Fri Mar 13 20:14:15 CET 2026
Method   : POST
URI      : /api/echo
Body     : {"firstname":"gianni","lastname":"alberico"}
Headers  : Content-Type: application/json; Accept: */*; X-Correlation-ID: my-correlation-id; User-Agent: PostmanRuntime/7.51.1; 

2026-03-13 20:14:15 INFO  [http-nio-8080-exec-9] my-correlation-id i.g.giannialberico.HttpLoggingFilter - 
---------- HTTP RESPONSE ----------
Date     : Fri Mar 13 20:14:15 CET 2026
Status   : 200
Body     : {"firstname":"gianni","lastname":"alberico"}
Headers  : X-Correlation-ID: my-correlation-id | Content-Length: 44 | 
Size     : 44 B
Duration : 2 ms
```
