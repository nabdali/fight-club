const URLS = {
  user:        import.meta.env.VITE_USER_URL        ?? 'http://localhost:8084',
  character:   import.meta.env.VITE_CHARACTER_URL   ?? 'http://localhost:8082',
  arena:       import.meta.env.VITE_ARENA_URL        ?? 'http://localhost:8083',
  leaderboard: import.meta.env.VITE_LEADERBOARD_URL ?? 'http://localhost:8085',
  gateway:     import.meta.env.VITE_GATEWAY_URL     ?? 'http://localhost:8080',
}

export default URLS
