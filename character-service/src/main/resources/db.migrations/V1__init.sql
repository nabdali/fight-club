CREATE TABLE character_type (
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(50) NOT NULL UNIQUE,
    strength INT NOT NULL DEFAULT 0,
    health   INT NOT NULL DEFAULT 0
);


CREATE TABLE character (
    id                BIGSERIAL PRIMARY KEY,
    user_id           BIGINT       NOT NULL,
    name              VARCHAR(100) NOT NULL,
    character_type_id BIGINT       NOT NULL REFERENCES character_type(id),
    level             INT          NOT NULL DEFAULT 1,
    experience        INT          NOT NULL DEFAULT 0,
    created_at        TIMESTAMP    NOT NULL DEFAULT now()
);
