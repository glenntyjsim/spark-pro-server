# Running the Project with Docker

This section provides instructions to build and run the project using Docker.

## Prerequisites

- Ensure Docker and Docker Compose are installed on your system.
- Docker version: Compatible with `docker/dockerfile:1` syntax.
- Maven version: 3.8.5 or later.
- Java version: OpenJDK 17.

## Build and Run Instructions

1. Clone the repository and navigate to the project root directory.
2. Build and start the application using Docker Compose:

   ```bash
   docker-compose up --build
   ```

3. Access the application at `http://localhost:8080`.

## Configuration

- The application exposes port `8080`.
- Modify the `application.properties` file for custom configurations.
- Uncomment the `env_file` line in the `docker-compose.yml` file if an `.env` file is used for environment variables.

## Notes

- The `Dockerfile` is configured to build the application using Maven and run it with OpenJDK 17.
- The `docker-compose.yml` file defines a single service `app` with automatic restart policy and port mapping.

For further details, refer to the project documentation or contact the development team.