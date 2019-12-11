# WillItRain
Will It Rain or Willi Train ?  ¯\_(ツ)_/¯

### WillItRain is an API that provides rain information according to the location.
#### Little notes 
  - MVC pattern
  - MySQL DB
  - Java 8
  - Docker needed

## Installation
Docker will handle the installation, if you don't have it yet follow these instructions

 - [Time to embark](https://docs.docker.com/install/)

If you have it, do this

```sh
//Go in your project folder
$ docker-compose build
$ docker-compose up
```

## API endpoints
- Use localhost:4567

| Request Method | Endpoint | Description |
| ------ | ------ | ------ |
| GET | /api/user/:id | Return a user by his id |
| DELETE | /api/user/:id | Delete a user by his id |
| PUT | /api/user/:id | Modify a user by his id |
| POST | /api/register | register a user, deliver token |
| POST | /api/loging | log in a user, deliver token |
| GET | /api/user/:id/willitrain/:location | Return the weather depending on location (posx, posy) |
| GET | /api/user/:id/willitrain/:city | Return the weather depending on city (use location endpoint) |
| GET | /api/frontpage | Return all the location of the current user |
| POST | /api/frontpage/location | Set a location for the current user |

# Dependencies documentation

## Spark java
**The version of spark we use is 2.9.1**

The Spark Java micro-framework documention is really gud, check this out
  - [Spark doc](http://sparkjava.com/documentation)
  
You can also use the javadoc which contain more detailled information
  - [javadoc for Spark 2.9.1](https://javadoc.io/doc/com.sparkjava/spark-core/latest/index.html)
  
## Auth0 java
  - [Auth0 java doc](https://auth0.com/docs/jwt) 
  - [javadoc for auth0 3.8.3](https://javadoc.io/doc/com.auth0/java-jwt/latest/index.html)

## JSON java
  - [javadoc for org.json](https://javadoc.io/static/org.json/json/20190722/index.html?org/json/package-summary.html)
  
## SQL java
 - [javadoc for sql java](https://docs.oracle.com/javase/8/docs/api/index.html?java/sql/package-summary.html)

## Google GSON java
  - [javadoc for Gson](https://javadoc.io/static/com.google.code.gson/gson/2.8.0/index.html?com/google/gson/Gson.html)
  
