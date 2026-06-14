# Fight Club — Frontend

React + TypeScript + Vite

## Prérequis

- Node.js 18+
- Les services backend démarrés (local ou distant)

## Installation

```bash
npm install
```

## Démarrage

### Local (services sur localhost)

```bash
npm run dev
```

Ouvre [http://localhost:5173](http://localhost:5173)

Les URLs des services sont configurées dans `.env` :

```
VITE_USER_URL=http://localhost:8084
VITE_CHARACTER_URL=http://localhost:8082
VITE_ARENA_URL=http://localhost:8083
VITE_LEADERBOARD_URL=http://localhost:8085
VITE_GATEWAY_URL=http://localhost:8080
```

### Prod (serveur distant)

```bash
npm run build:prod
```

Les URLs pointent vers `141.94.65.250` — configurées dans `.env.prod`.

## Pages

| Page | URL | Description |
|------|-----|-------------|
| Arena | `/arena` | Lancer et rejoindre des combats |
| Users | `/users` | Liste des utilisateurs, créer un compte |
| Characters | `/characters` | Liste des personnages, créer un personnage |
| Leaderboard | `/leaderboard` | Classement par victoires / défaites |
| Live Feed | `/feed` | Stream SSE des events Kafka en temps réel |

## Live Feed (SSE)

La page **Live Feed** se connecte via `EventSource` au gateway (`GET /events/stream`) qui consomme les topics Kafka suivants :

| Topic | Événement |
|-------|-----------|
| `fight.created` | Un combat a été lancé |
| `fight.ended` | Un combat s'est terminé |
| `stats.update` | Le leaderboard a été mis à jour |

Les events déclenchés depuis l'interface (création de personnage, inscription) apparaissent aussi dans le feed.

## Lancer un combat (flow)

1. Aller sur **Characters** → noter l'ID de deux personnages
2. Aller sur **Arena** → entrer l'ID du fighter 1 → cliquer **Challenge**
3. Entrer l'ID du fighter 2 → cliquer **Enter the Arena**
4. Le résultat s'affiche avec le gagnant
5. Aller sur **Live Feed** → voir les events `fight.created` et `fight.ended`
