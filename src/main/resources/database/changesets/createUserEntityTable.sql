--liquibase formatted sql

--changeset damianbodzioch:2

CREATE TABLE users
(
    id                      SERIAL      PRIMARY KEY,
    username                VARCHAR     NOT NULL            CONSTRAINT uq_users_username UNIQUE,
    password                VARCHAR     NOT NULL
);

ALTER SEQUENCE users_id_seq INCREMENT BY 50;

CREATE UNIQUE INDEX idx_movie_imdb_id ON movie (imdb_id);
CREATE UNIQUE INDEX idx_users_username ON users (username);
