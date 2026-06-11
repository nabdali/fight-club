-- Type cycle: Assassin > Mage > Tank > Archer > Assassin
INSERT INTO character_type (name, strength, health) VALUES
  ('Assassin', 90,  80),
  ('Mage',    100,  70),
  ('Tank',     60, 150),
  ('Archer',   80,  90)
ON CONFLICT (name) DO NOTHING;

INSERT INTO character (user_id, name, character_type_id, level, experience, created_at) VALUES
  (1, 'Shadow',   (SELECT id FROM character_type WHERE name = 'Assassin'), 5, 450, now()),
  (2, 'Gandalf',  (SELECT id FROM character_type WHERE name = 'Mage'),     3, 210, now()),
  (3, 'Ironclad', (SELECT id FROM character_type WHERE name = 'Tank'),     4, 320, now()),
  (1, 'Hawkeye',  (SELECT id FROM character_type WHERE name = 'Archer'),   2, 100, now());
