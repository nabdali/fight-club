INSERT INTO fight (character1_id, character2_id, winner_id, status, created_at, ended_at) VALUES
  (1, 2, 1, 'ENDED',   now() - interval '2 hours',      now() - interval '1 hour 55 minutes'),
  (2, 3, 3, 'ENDED',   now() - interval '1 hour',       now() - interval '55 minutes'),
  (1, 3, 1, 'ENDED',   now() - interval '30 minutes',   now() - interval '25 minutes'),
  (2, 1, 2, 'ENDED',   now() - interval '10 minutes',   now() - interval '8 minutes'),
  (1, 2, NULL, 'PENDING', now(), NULL);
