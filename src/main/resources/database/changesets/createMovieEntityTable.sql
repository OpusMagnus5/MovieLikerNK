--liquibase formatted sql

--changeset damianbodzioch:1

CREATE TABLE movie
(
    id                  SERIAL      PRIMARY KEY,
    title               VARCHAR     NOT NULL,
    year                SMALLINT    NOT NULL,
    director            VARCHAR     NOT NULL,
    genre               VARCHAR,
    plot                VARCHAR,
    poster              VARCHAR,
    imdb_id             INTEGER     NOT NULL            CONSTRAINT uq_movie_imdb UNIQUE
);

ALTER SEQUENCE movie_id_seq INCREMENT BY 50;