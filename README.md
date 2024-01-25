# CoEx_Backend

Welcome to the `coex_backend` project! This backend application serves as the REST API for the `coex_frontend` project.  The User model/repository/controller is meant to act as the template for any other objects needed in the project.

## Overview

`coex_backend` is a Maven project that provides a RESTful API to support the `coex_frontend` application. It utilizes Spring Boot and integrates with Elasticsearch for data storage.

## Getting Started

### Prerequisites

Before you begin, make sure you have the following installed:

- Java SE 20 (JDK 20)
- Docker
- Maven
- Eclipse
- Postman

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/RileyAine/coex_backend.git
   cd coex_backend
   ```

2. Import as Existing Maven Project to Eclipse
3. Run as Maven Project

## Configuration

The project uses Docker containers for Elasticsearch and the Spring Boot application. The configurations are set in the dockerfile.

## Usage

The API provides endpoints for interacting with data related to CooperativeExistence.com. You can find the API documentation for available endpoints and request/response formats [here](#api-documentation).

## API Documentation

### Endpoint: Users

#### **GET**: /api/users
- **Sample Request**:
```http://<IP>:<PORT>/api/users/```
- **Response Format**:
```
Status: 200 OK
{
    "content": [
        {
            "id": "", // Unique identifier
            "email": "", // Unique identifier
            "createdAt": "",
            "updatedAt": "",
            "accessLevel": "",
            "isVerified": "",
            "password": "",
            "firstName": "",
            "lastName": ""
        }
    ],
    "pageable": {
        "pageNumber": #,
        "pageSize": #,
        "sort": {
            "empty": bool,
            "sorted": bool,
            "unsorted": bool
        },
        "offset": #,
        "paged": bool,
        "unpaged": bool
    },
    "last": bool,
    "totalPages": #,
    "totalElements": #,
    "first": bool,
    "size": #,
    "number": #,
    "sort": {
        "empty": bool,
        "sorted": bool,
        "unsorted": bool
    },
    "numberOfElements": #,
    "empty": bool
}
```

#### **GET**: /api/users/{idOrEmail}
- **Sample Request**:
```http://<IP>:<PORT>/api/users/df10c053-09fc-4a16-9200-76730e4a1e37 || sample@email.com```
- **Response Format**:
```
Status: 200 OK
{
    "id": "", // Unique identifier
    "email": "", // Unique identifier
    "createdAt": "",
    "updatedAt": "",
    "accessLevel": "",
    "isVerified": "",
    "password": "",
    "firstName": "",
    "lastName": ""
}
```
  
####  **POST**: /api/users
- **Sample Request**:
```
http://<IP>:<PORT>/api/users/

body:
{
    "email":"sample@email.com",
    "firstName": "first",
    "lastName": "last",
    "password": "pass"
}
```
- **Response Format**:
```
Status: 200 OK
{
    "id": "", // Unique identifier
    "email": "", // Unique identifier
    "createdAt": "",
    "updatedAt": "",
    "accessLevel": "",
    "isVerified": "",
    "password": "",
    "firstName": "",
    "lastName": ""
}
```

####  **PUT**: /api/users/{id}
- **Sample Request**:
```
http://<IP>:<PORT>/api/users/df10c053-09fc-4a16-9200-76730e4a1e37

body:
{
    "email": "crab@pants.com", // editable
    "accessLevel": "USER", // editable with USER|MODERATOR|ADMIN
    "isVerified": "false", // editable with true|false
    "password": "crabbypassword", // editable
    "firstName": "Crab", // editable
    "lastName": "Pants" // editable
}
```
- **Response Format**:
```
Status: 200 OK
{
    "id": "", // Unique identifier
    "email": "", // Unique identifier
    "createdAt": "",
    "updatedAt": "",
    "accessLevel": "",
    "isVerified": "",
    "password": "",
    "firstName": "",
    "lastName": ""
}
```

#### **DELETE**: /api/users/{id}
- **Sample Request**:
```http://<IP>:<PORT>/api/users/<sample>@<domain_name>.<top_level_domain>```
- **Response Format**:
```
Status: 200 OK
```

#### **Description**
The Users endpoint provides the user model by which the frontend user object is defined.  All properties are configured with constraints.  The available properties are as followes:
- String id: default: UUID.getRandom().toString(), uneditable by frontend
- Date createdAt: default: new Date(), represented by a String on the frontend, uneditable by frontend
- Date updatedAt: default: new Date(), represented by a String on the frontend, uneditable by frontend
- String accessLevel: default: USER, USER|MODERATOR|ADMIN
- String email: formed as <sample>@<domain_name>.<top_level_domain>
- String isVerified: default: false, true|false, based on email verification
- String password: plaintext, frontend responsible for encryption
- String firstName: user's first name
- String lastName: user's last name
  
## License

This project is licensed under the [MIT License](LICENSE).
