# RabbitMQ Integration Service

This application is a messaging service built using Spring Boot and RabbitMQ. It enables JSON-based messaging and data transfer between components and stores the data in a PostgreSQL database.

## Features

- Send plain text messages via RabbitMQ
- Transfer user data using JSON messages via RabbitMQ
- Fetch and send a list of persons from the database via RabbitMQ

## Technologies Used

- Spring Boot
- RabbitMQ
- PostgreSQL
- Jackson for JSON serialization/deserialization

## Prerequisites

- Java 11 or higher
- Maven
- PostgreSQL
- RabbitMQ

## Getting Started

1. **Clone the repository**

    ```bash
    git clone https://github.com/thqu1et/RabbitMQ-Service.git
    cd rabbitmq-integration-service
    ```

2. **Configure the PostgreSQL Database**

   Ensure you have PostgreSQL installed and running. Create a database and update the `application.properties` file with your database credentials.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5434/postgres
    spring.datasource.username=postgres
    spring.datasource.password=qwerty
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.show-sql=true
    ```

3. **Configure RabbitMQ**

   Ensure you have RabbitMQ installed and running. Update the `application.properties` with your RabbitMQ credentials and configuration.

    ```properties
    spring.rabbitmq.host=127.0.0.1
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=guest
    spring.rabbitmq.password=guest
    spring.rabbitmq.virtual-host=cpp

    queue.name=FirstQueue
    data.routing.key=data_routing
    data.exchange=exchange

    rabbit.queue.name=JsonQueue
    rabbit.routing.json.name=routing_json_key
    ```

4. **Build the Project**

   Use Maven to build the project:

    ```bash
    mvn clean install
    ```

5. **Run the Application**

   Start the Spring Boot application:

    ```bash
    mvn spring-boot:run
    ```

   The application will start and be accessible at `http://127.0.0.1:8084`.

## API Endpoints

### Send Plain Text Message

- **URL**: `/api/v1/rabbit/send`
- **Method**: `GET`
- **Query Parameter**: `message`

  Example: `/api/v1/rabbit/send?message=HelloWorld`

### Transfer User Data

- **URL**: `/api/v1/rabbit/transfer`
- **Method**: `POST`
- **Request Body**: `User`

    ```json
    {
        "id": 1,
        "name": "John Doe",
        "email": "john.doe@example.com"
    }
    ```

### Send Persons Data

- **URL**: `/api/v1/rabbit/persons`
- **Method**: `POST`
- **Response Body**: List of `PersonsDTO`

  This endpoint fetches the first 30 persons from the database and sends them as a JSON message via RabbitMQ.

## Exception Handling

The application includes custom exception handling to manage various scenarios like data retrieval issues or messaging errors.

## Logging

The application uses `Slf4j` for logging purposes. Logs will provide information on application events and errors.

## Conclusion

This RabbitMQ Integration Service demonstrates the use of Spring Boot and RabbitMQ for building a messaging service. It covers basic messaging operations and includes exception handling and logging for better maintainability and debugging.


# RabbitMQ Consumer Service

This application is a RabbitMQ consumer service built using Spring Boot. It listens to a RabbitMQ queue for incoming JSON messages, processes the data, and saves it to a PostgreSQL database.

## Features

- Listen to a RabbitMQ queue for incoming messages
- Deserialize JSON messages to `PersonsDTO` objects
- Save data to PostgreSQL database if the entity doesn't already exist

## Technologies Used

- Spring Boot
- RabbitMQ
- PostgreSQL
- Jackson for JSON serialization/deserialization

## Prerequisites

- Java 11 or higher
- Maven
- PostgreSQL
- RabbitMQ

## Getting Started

1. **Configure the PostgreSQL Database**

   Ensure you have PostgreSQL installed and running. Create a database and update the `application.properties` file with your database credentials.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5435/postgres
    spring.datasource.username=postgres
    spring.datasource.password=qwerty
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.show-sql=true
    ```

2. **Configure RabbitMQ**

   Ensure you have RabbitMQ installed and running. Update the `application.properties` with your RabbitMQ credentials and configuration.

    ```properties
    spring.rabbitmq.host=127.0.0.1
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=guest
    spring.rabbitmq.password=guest
    spring.rabbitmq.virtual-host=cpp
    ```

3. **Build the Project**

   Use Maven to build the project:

    ```bash
    mvn clean install
    ```

4. **Run the Application**

   Start the Spring Boot application:

    ```bash
    mvn spring-boot:run
    ```

   The application will start and be accessible at `http://127.0.0.1:8085`.

## How It Works

- The application listens to the `JsonQueue` RabbitMQ queue for incoming messages.
- When a message is received, it is logged and deserialized from JSON into a list of `PersonsDTO` objects.
- The service checks if each person entity already exists in the database by its ID.
- If the entity does not exist, it is saved to the `persons` table in the PostgreSQL database.

## Exception Handling

The application includes basic exception handling to log errors that occur during message processing or database operations.

## Logging

The application uses `Slf4j` for logging purposes. Logs will provide information on received messages, skipped entities, and any errors encountered during processing.

## Conclusion

This RabbitMQ Consumer Service demonstrates the use of Spring Boot, RabbitMQ, and PostgreSQL for processing messages in a microservice architecture. It covers message handling, data persistence, and exception logging to ensure reliable operation.
