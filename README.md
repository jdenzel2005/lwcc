# Lexware Coding Challenge (lwcc)

## Getting Started and Requirements

* java 21
* a recent version of docker

## Running the application

### Locally

1. Start the database: `docker compose up`. The initial schema 'lwcc' will be created.
2. Run the lwcc backend application: `./gradlew bootRun`
3. Run lwcc frontend in folder ui: `ng serve`

## What to Look at!

The lwcc backend is implemented using a clean architecture approach.
Package structure and their purpose:

```
lwcc/
├── config/                 # Global technical configurations
│   ├── jackson/
│   └── jpa/
│
├── customers/              # Customer service implementation
│   ├── api/                # Exposed API
│   ├── application/        # Use cases of the application
│   ├── domain/             # Business domain without any technical framework deps
│   └── infrastructure/     # Infrastructure e.g. DB, Messaging, etc.
│       └── persistence/    # Persistence layer, JPA
│
├── shared/                 # Common shared classes in the customer service
│   ├── api/                # Common API interface
│   ├── domain/             # Common domain model classes
│   ├── infrastructure/
│   │   └── persistence/    # Common JPA domain class
│   └── uuid/               # JPA UUID generator
│
└── LexwareCodingChallengeApplication.java
```

## Remark

No security concept was implemented. API is public.

## Gradle tasks

| Task                       | Command                          |
|----------------------------|----------------------------------|
| Run all tests              | `./gradlew test`                 |
| Generate OpenApi docs      | `./gradlew generateOpenApiDocs`  |


## Service APIs

The API that is being exposed by the backend services is documented in an [OpenAPI](https://www.openapis.org/) specification.
The specification files are available at the following locations:

| Service | OpenAPI Specification                      |
|---------|--------------------------------------------|
| lwcc    | [./api/lwcc-api.yaml](./api/lwcc-api.yaml) |

Swagger UI can be found here (local profile only): [Swagger UI](http://localhost:8001/api/swagger-ui/index.html)


## Docker compose

see file ./docker-compose.yaml

| Services    | Description       |
|-------------|-------------------|
| postgres    | Database for lwcc |
