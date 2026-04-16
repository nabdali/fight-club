## Définition du besoin

Le projet consiste à développer une plateforme de jeu en ligne qui permet aux utilisateurs de s'inscrire, et de jouer à un jeu de d'arène de combat en temps réel. Les utilisateurs pourront créer des personnages, personnaliser leurs compétences et participer à des combats contre d'autres joueurs.

## Fonctionnalités principales

- Inscription et authentification des utilisateurs
- Création et personnalisation de personnages
- Création d'arènes de combat
- Rejoindre une arène et participer à des combats en temps réel
- Système de classement et de récompenses

## Architecture technique

- ALI : Microservice arène de combat : Gère la logique du jeu, les combats et les interactions entre les joueurs.
- Microservice de gestion des utilisateurs : Gère l'inscription, l'authentification et les profils des utilisateurs.
- Microservice de gestion des personnages : Permet aux utilisateurs de créer et personnaliser leurs personnages.
- Microservice de classement : Gère le classement des joueurs et les récompenses.
- API Gateway : Point d'entrée unique pour les clients, qui redirige les requêtes vers les microservices appropriés.

## Technologies utilisées

- Java & Spring Boot pour les microservices
- PostgreSQL pour la base de données
- RabbitMQ & Kafka pour la communication entre les microservices

## Rôles

- Arthur : Lead développeur, responsable du microservice de gestion des utilisateurs et de l'API Gateway, cohérence globale de l'archi.
- Théo : PO, Responsable du microservice de gestion des personnages, développement de la logique de personnalisation.
- Jojo : Scrum, Responsable du microservice de gestion de classement, développement du système de classement et de récompenses.
- Ali : CTO, Responsable du microservice d'arène de combat, développement de la logique de jeu et des combats en temps réel.
