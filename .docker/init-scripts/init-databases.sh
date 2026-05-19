#!/bin/sh
set -e

DATABASES="user_db character_db arena_db leaderboard_db"

echo ">>> Verification et creation des bases de donnees (init)..."

for db in $DATABASES; do
    EXISTS=$(psql --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -tAc "SELECT 1 FROM pg_database WHERE datname = '$db'")
    if [ "$EXISTS" = "1" ]; then
        echo ">>> Base '$db' existe deja — OK"
    else
        psql --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -c "CREATE DATABASE $db;"
        echo ">>> Base '$db' creee avec succes"
    fi
done
