#!/bin/bash
set -e

PGHOST="postgres"
PGUSER="fightclub"
PGPASSWORD="fightclub"
PGDB="fightclub"
export PGPASSWORD

DATABASES="user_db character_db arena_db leaderboard_db"

echo ">>> Vérification et création des bases de données..."

for db in $DATABASES; do
    EXISTS=$(psql -h "$PGHOST" -U "$PGUSER" -d "$PGDB" -tAc "SELECT 1 FROM pg_database WHERE datname = '$db'")
    if [ "$EXISTS" = "1" ]; then
        echo ">>> Base '$db' existe déjà — OK"
    else
        psql -h "$PGHOST" -U "$PGUSER" -d "$PGDB" -c "CREATE DATABASE $db;"
        echo ">>> Base '$db' créée avec succès"
    fi
done

echo ">>> Toutes les bases de données sont prêtes."

