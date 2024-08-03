# Emergency Management

This project uses Spring Boot 3.1.x or higher; and can be run in dev mode simply by hitting:
```bash
./mvnw clean spring-boot:run
```

- The application will spin up a Postgres container (when starting up) from `compose.yml` file in the root of the project and leave it running.

- The schema will be initialized within the container with some dummy data, using Flyway migrations.

- A pre-configured postman collection in the root of the project (`postman/emergency-management.json`) is added in order to test all APIs.

#### Requirements
- Java 21 (expose `$JAVA_HOME` environment variable)
- Docker (with Compose) installed & running locally
- Internet connection to install dependencies & required docker images

**Note:** If you are using **SDKMAN**, then just go to the root of the project and apply the `.sdkmanrc` file to install the right version of the JDK.
Go to root project then run:
```bash
sdk env
```

### Running the Application
You can run your application that enables live coding with (Spring DevTools) using:
```bash
./mvnw clean spring-boot:run
```

### Packaging and Running the Application
The application can be packaged using:
```bash
./mvnw clean package
```

The application is now runnable using:
```bash
java -jar target/*.jar
```
- Please just make sure you startup the docker compose file manually before running JAR file using :
```bash
docker-compose up -d
```

> Happy coding :P


