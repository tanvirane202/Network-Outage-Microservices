# Network-Outage-Microservices
# NetworkGuardAI

NetworkGuardAI is a network outage and incident management system built using Java, Spring Boot and Microservices.

The project manages customers, network outages, technicians and notifications. It also includes an AI-based recommendation service for network incidents.

## Tech Stack

* Java 21
* Spring Boot
* Spring Cloud
* Spring Security
* JWT
* Spring Data JPA / Hibernate
* MySQL
* Aiven
* Eureka
* Spring Cloud Gateway
* Apache Kafka
* AI API
* REST APIs
* Postman
* Maven

## Microservices

The project contains the following microservices:

* Eureka Server
* API Gateway
* Customer Service
* Outage Service
* Technician Service
* Notification Service
* AI Recommendation Service

## Eureka Service Discovery

Eureka Server is used for service registration and discovery.

Each microservice registers itself with Eureka, allowing services to discover and communicate with each other.

Eureka Dashboard:

```text
http://localhost:8761
```

Service registration flow:

```text
Microservice
     ↓
Eureka Server
     ↓
Service Registration
```

## API Gateway

API Gateway acts as the main entry point for client requests.

It handles request routing and JWT authentication and validation.

```text
Client
   ↓
API Gateway
   ↓
JWT Validation
   ↓
Required Microservice
```

## Customer Service

Customer Service manages customer information.

Implemented operations include:

* Customer creation
* Customer retrieval
* Customer update
* Customer deletion

Customer data is stored using MySQL and the service is connected with an Aiven MySQL database.

## Outage Service

Outage Service manages network outages and incidents.

It handles the main outage workflow, including investigation and completion of outages.

The service also communicates with the Technician Service and AI Recommendation Service.

```text
                    Outage Service
                         |
             ┌───────────┴───────────┐
             ↓                       ↓
    Technician Service      AI Recommendation
                                  Service
```

Kafka is also used by the Outage Service to publish outage events.

## Technician Service

Technician Service manages technician information.

The Outage Service communicates with this service when technician-related operations are required.

```text
Outage Service
      ↓
Technician Service
      ↓
Technician Information
```

## Notification Service

Notification Service processes notification events generated from network outages.

Kafka is used for communication between the Outage Service and Notification Service.

```text
Outage Service
      ↓
Outage Event
      ↓
    Kafka
      ↓
Notification Service
      ↓
Notification Processing
```

The Notification Service consumes the events published by the Outage Service.

## AI Recommendation Service

AI Recommendation Service provides AI-based recommendations for network incidents.

The Outage Service sends outage-related information to the AI Recommendation Service.

```text
Outage Information
       ↓
AI Recommendation Service
       ↓
AI Model
       ↓
Recommendation
```

The AI functionality is separated into its own microservice instead of being directly implemented inside the Outage Service.

## Inter-Service Communication

The project uses both REST and Kafka for communication between microservices.

REST is used for direct service-to-service communication.

```text
Outage Service
      ├──→ Technician Service
      └──→ AI Recommendation Service
```

Kafka is used for event-based communication.

```text
Outage Service
      ↓
    Kafka
      ↓
Notification Service
```

## Security

JWT-based authentication is implemented through the API Gateway.

The user logs in and receives a JWT token. The token is then validated by the Gateway before the request is forwarded to the required service.

```text
User
 ↓
Login
 ↓
JWT Token
 ↓
API Gateway
 ↓
JWT Validation
 ↓
Microservice
```

## Database

MySQL is used for storing application data.

The project uses an Aiven MySQL database.

Database integration is implemented using:

* Spring Data JPA
* Hibernate
* MySQL

Customer and outage-related data are persisted in the database.

## Kafka

Apache Kafka is used for event-based communication between services.

The project includes Kafka configuration, an outage event producer and a notification event consumer.

```text
Outage Service
      ↓
OutageEventProducer
      ↓
Kafka
      ↓
NotificationEventConsumer
      ↓
Notification Service
```

## AI Recommendation Flow

The AI recommendation functionality is handled separately from the Outage Service.

```text
Outage Service
      ↓
AI Recommendation Client
      ↓
AI Recommendation Service
      ↓
AI Model
      ↓
Recommendation
```

This keeps the AI functionality separated from the main outage management logic.

## APIs Tested

The APIs were tested using Postman through the API Gateway.

Some tested APIs:

```text
GET http://localhost:8085/outages/my

GET http://localhost:8085/outages/22/complete

GET http://localhost:8085/outages/22/investigate
```

Customer, Technician, AI Recommendation and Notification APIs were also tested during development.

## Project Structure

```text
Network-Microservices/
│
├── eureka-server/
│
├── api-gateway1/
│
├── customer-service/
│
├── outage-service/
│
├── Technician-service/
│
├── Notification-service/
│
└── ai-recommendation-service/
```

## How to Run

1. Clone the repository and open the project in Eclipse or any Spring Boot IDE.

2. Configure the MySQL / Aiven database details in the required services.

3. Start the **Eureka Server** first.

4. Open the Eureka Dashboard:

```text
http://localhost:8761
```

5. Start the remaining microservices one by one:

```text
API Gateway
Customer Service
Outage Service
Technician Service
Notification Service
AI Recommendation Service
```

6. Refresh the Eureka Dashboard:

```text
http://localhost:8761
```

The running microservices should appear as registered services.

7. Once the required services are registered, use **Postman** to test the APIs through the API Gateway.

Example:

```text
http://localhost:8085/outages/my
```

## Project Flow

```text
                         NetworkGuardAI
                               |
                               ↓
                         API Gateway
                       JWT Authentication
                               |
                               ↓
                         Eureka Server
                               |
        ┌──────────────────────┼──────────────────────┐
        ↓                      ↓                      ↓
 Customer Service       Outage Service        Technician Service
                               |
                    ┌──────────┴──────────┐
                    ↓                     ↓
             AI Recommendation        Kafka
                  Service                |
                                         ↓
                                  Notification
                                    Service
```


