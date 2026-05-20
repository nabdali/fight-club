CREATE TABLE fight (
    id          BIGSERIAL PRIMARY KEY,
    character1_id BIGINT       NOT NULL,
    character2_id BIGINT,
    winner_id   BIGINT,
    status      VARCHAR(50)  NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    ended_at    TIMESTAMP
);
