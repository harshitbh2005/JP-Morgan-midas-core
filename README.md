
# JPMorgan Chase — Midas Core (Forage)

A distributed transaction processing system built with Java, Spring Boot, Kafka, SQL, and REST APIs as part of JPMorgan Chase’s Software Engineering Virtual Experience on Forage.

## What it does
- Processes financial transactions via Kafka
- Validates balances and users
- Stores data in an SQL database (H2 + JPA)
- Integrates an external Incentive microservice
- Exposes REST APIs to query user balances

## Tech stack
Java 17, Spring Boot, Kafka, H2, JPA, Docker, REST, Testcontainers

## How to run
```bash
cd services
java -jar transaction-incentive-api.jar
cd ..
./mvnw spring-boot:run

