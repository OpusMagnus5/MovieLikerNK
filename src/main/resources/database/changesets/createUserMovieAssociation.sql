--liquibase formatted sql

--changeset damianbodzioch:3

ALTER TABLE movie ADD COLUMN users_id INTEGER REFERENCES users (id);