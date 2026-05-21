#!/bin/bash

CONTAINER_NAME="fight-club-postgres"
DB_USER="fightclub"
USER_DB="user_db"
CHAR_DB="character_db"

echo "Creation ou mise a jour des user"
docker exec -i $CONTAINER_NAME psql -v ON_ERROR_STOP=1 -U $DB_USER -d $USER_DB <<EOF
INSERT INTO users (id, email, pseudo, password, victory_counter, defeat_counter)
OVERRIDING SYSTEM VALUE
VALUES
    (1056, 'joseph@gmail.com', 'Sephoyoooo', 'test', 15, 2),
    (1057, 'arthur@gmail.com', 'Zwinxx', 'test', 8, 12),
    (1058, 'theo@gmail.com', 'Nanash', 'test', 25, 0)
ON CONFLICT (id) DO UPDATE SET
    email = EXCLUDED.email,
    pseudo = EXCLUDED.pseudo,
    password = EXCLUDED.password,
    victory_counter = EXCLUDED.victory_counter,
    defeat_counter = EXCLUDED.defeat_counter;
EOF

if [ $? -eq 0 ]; then
    echo "User crée ou mis a jour"
else
    echo "Erreur"
fi

echo -e "\nCreation ou mise a jour des types de personnages."
docker exec -i $CONTAINER_NAME psql -v ON_ERROR_STOP=1 -U $DB_USER -d $CHAR_DB <<EOF
INSERT INTO character_type (id, name, strength, health)
OVERRIDING SYSTEM VALUE
VALUES
    (1, 'Assasin', 90, 50),
    (2, 'Mage', 5, 70),
    (3, 'Archer', 10, 150)
    (4, 'Tank', 2, 300)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    strength = EXCLUDED.strength,
    health = EXCLUDED.health;
EOF

if [ $? -eq 0 ]; then
    echo "Types de personnages crée ou mis a jour !"
else
    echo "Erreur types de personnages."
fi

echo -e "\nCréation ou mise a jour des personnages."
docker exec -i $CONTAINER_NAME psql -v ON_ERROR_STOP=1 -U $DB_USER -d $CHAR_DB <<EOF
INSERT INTO character (name, character_type_id, user_id, level, experience, created_at)
VALUES
    ('Sephoyoooo', 2, 1056, 10, 450, NOW()),
    ('Zwinxx', 1, 1057, 5, 120, NOW()),
    ('Nanash', 3, 1058, 20, 1500, NOW())
ON CONFLICT (name) DO UPDATE SET
    character_type_id = EXCLUDED.character_type_id,
    user_id = EXCLUDED.user_id,
    level = EXCLUDED.level,
    experience = EXCLUDED.experience,
    created_at = EXCLUDED.created_at;
EOF

if [ $? -eq 0 ]; then
    echo "Personnages crée ou mis a jour !"
else
    echo "Erreur personnages."
fi

echo -e "\nMon sac est fait"
