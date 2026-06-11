INSERT INTO users (email, pseudo, password, victory_counter, defeat_counter) VALUES
  ('alice@fight.club',   'alice',   'password123', 2, 1),
  ('bob@fight.club',     'bob',     'password123', 1, 2),
  ('charlie@fight.club', 'charlie', 'password123', 1, 1)
ON CONFLICT DO NOTHING;
