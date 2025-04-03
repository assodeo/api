# Assodeo API

A Restful API for **Assodeo**, a platform for managing associations.

This project is using Spring Boot 3.4.

This API provides endpoints for managing users, associations, events, 
and other resources.

## Installation

Assuming you are using IntelliJ IDEA, you can run the project by following these steps:
1. Clone the repository (File → New → Project from Version Control)
2. Run the server (Run → Run... → 'AssodeoApplication')

The API will be available at `http://localhost:8080` by default.

## Project Structure

The project is structured as follows:

```
|-- src
|   |-- main
|   |   |-- java
|   |   |   |-- com.assodeo.api
|   |   |   |   |-- config # Configuration classes
|   |   |   |   |-- controller # REST controllers
|   |   |   |   |-- AssodeoApplication.java
|   |   |   |-- resources
|   |   |       |-- db.changelog # Database changelogs
|   |   |       |-- application.properties
|   |   |       |-- application-dev.properties
|   |   |       |-- application-prod.properties
```
