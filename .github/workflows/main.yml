# Stage 1: Build the native executable with GraalVM
FROM ghcr.io/graalvm/graalvm-community:24 AS builder

WORKDIR /app

# Copy the Gradle configuration files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Make the gradlew script executable
RUN chmod +x ./gradlew

# Copy the source code
COPY src src

# Build the native executable
RUN ./gradlew nativeCompile

# Stage 2: Create a minimal runtime image
FROM debian:bookworm-slim

WORKDIR /app

# Install minimal runtime dependencies
RUN apt-get update && apt-get install -y \
    libz-dev \
    libpq5 \
    && rm -rf /var/lib/apt/lists/*

# Copy the native executable from the builder stage
COPY --from=builder /app/build/native/nativeCompile/jokegen /app/jokegen

# Make the executable runnable
RUN chmod +x /app/jokegen

# Expose the application port
EXPOSE 8080

# Run the native executable
ENTRYPOINT ["/app/jokegen"]
