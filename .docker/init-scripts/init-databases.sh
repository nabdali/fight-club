#!/bin/bash
set -e

echo ">>> Création des bases de données si elles n'existent pas..."

for db in user_db character_db arena_db leaderboard_db; do
    psql --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
        -tc "SELECT 1 FROM pg_database WHERE datname = '$db'" \
        | grep -q 1 \
        || psql --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
               -c "CREATE DATABASE $db;"
    echo ">>> Base '$db' prête."
done
