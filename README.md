# JokeGenAlpha4

A Spring Boot application for generating jokes with GraalVM native image support.

## GitHub Actions Workflow

This project includes a GitHub Actions workflow that automatically builds a Docker image with a GraalVM native executable on every push to the main branch.

### Workflow Details

The workflow performs the following steps:

1. Checks out the code
2. Sets up Docker Buildx
3. Logs in to GitHub Container Registry (ghcr.io)
4. Extracts metadata for Docker image tagging
5. Builds a multi-stage Docker image:
   - First stage: Builds the native executable using GraalVM
   - Second stage: Creates a minimal runtime image with only the necessary dependencies
6. Pushes the image to GitHub Container Registry (only on main branch)

### Docker Image

The Docker image is built using a multi-stage Dockerfile:

- **Build Stage**: Uses GraalVM to compile the Spring Boot application to a native executable
- **Runtime Stage**: Uses a minimal Debian image with only the necessary runtime dependencies

This approach results in a small, efficient Docker image that starts up quickly and uses minimal resources.

## Running the Docker Image

Once the GitHub Actions workflow has completed, you can run the Docker image with:

```bash
docker pull ghcr.io/[your-username]/jokegen:latest
docker run -p 8080:8080 ghcr.io/[your-username]/jokegen:latest
```

## Local Development

To build the Docker image locally:

```bash
docker build -t jokegen:local .
docker run -p 8080:8080 jokegen:local
```

## Database Configuration

The application requires a PostgreSQL database. You can use the included `compose.yaml` file to start a PostgreSQL container:

```bash
docker-compose up -d
```