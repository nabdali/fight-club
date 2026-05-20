#!/bin/sh
set -e

PGHOST="postgres"
PGUSER="fightclub"
PGPASSWORD="fightclub"
PGDB="fightclub"
export PGPASSWORD

DATABASES="user_db character_db arena_db leaderboard_db"

echo ">>> Verification et creation des bases de donnees..."

for db in $DATABASES; do
    EXISTS=$(psql -h "$PGHOST" -U "$PGUSER" -d "$PGDB" -tAc "SELECT 1 FROM pg_database WHERE datname = '$db'")
    if [ "$EXISTS" = "1" ]; then
        echo ">>> Base '$db' existe deja — OK"
    else
        psql -h "$PGHOST" -U "$PGUSER" -d "$PGDB" -c "CREATE DATABASE $db;"
        echo ">>> Base '$db' creee avec succes"
    fi
done

echo ">>> Toutes les bases de donnees sont pretes."
