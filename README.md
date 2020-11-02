BBL-interview-api

Demo live in http://34.87.81.123/

Run Manual
```
* ./mvnw package && java -jar target/BBL-interview-api-0.0.1-SNAPSHOT.jar
```
Then go to this site
http://localhost:8080/


Docker

```
* docker build -t bbl-interview .

* docker run -d -p 80:8080 bbl-interview
```
Then go to this site
http://localhost/


We have 4 api follow this:

* Get /
* Get /getData
* Post /saveData
    * key
    * sum
* Get /transformKeyValue






