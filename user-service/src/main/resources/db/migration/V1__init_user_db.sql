CREATE TABLE users (
                       id       INT PRIMARY KEY,
                       email    VARCHAR(255) NOT NULL UNIQUE,
                       pseudo   VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);