BBL-interview-api

Demo live in http://xxxxxxxxxxx

Run Manual
```
./mvnw package && java -jar target/BBL-interview-api-0.0.1-SNAPSHOT.jar

http://localhost:8080/getData
```

Docker

```
docker build -t bbl-interview .\

docker run -d -p 80:8080 bbl-interview\

http://localhost/getData
```




