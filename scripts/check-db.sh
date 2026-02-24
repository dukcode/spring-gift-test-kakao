#!/bin/bash

# Check if PostgreSQL test container is running and ready
# Usage: ./scripts/check-db.sh

MAX_RETRIES=30
RETRY_INTERVAL=1

check_postgres() {
    docker compose ps postgres-test --format json 2>/dev/null | grep -q '"running"'
}

wait_for_postgres() {
    echo "Waiting for PostgreSQL to be ready..."
    for i in $(seq 1 $MAX_RETRIES); do
        if docker compose exec -T postgres-test pg_isready -U gift -d gift_test > /dev/null 2>&1; then
            echo "PostgreSQL is ready!"
            return 0
        fi
        echo "Attempt $i/$MAX_RETRIES - PostgreSQL not ready yet..."
        sleep $RETRY_INTERVAL
    done
    echo "Error: PostgreSQL failed to start within timeout"
    return 1
}

# Check if Docker is available
if ! command -v docker &> /dev/null; then
    echo "Error: Docker is not installed or not in PATH"
    exit 1
fi

# Check if Docker daemon is running
if ! docker info > /dev/null 2>&1; then
    echo "Error: Docker daemon is not running"
    exit 1
fi

# Start PostgreSQL if not running
if ! check_postgres; then
    echo "Starting PostgreSQL test container..."
    docker compose up -d postgres-test
fi

# Wait for PostgreSQL to be ready
wait_for_postgres
