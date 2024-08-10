CREATE TABLE village
(
    id   SERIAL PRIMARY KEY,
    code BIGINT       NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE village_part
(
    id           SERIAL PRIMARY KEY,
    code         BIGINT       NOT NULL,
    name         VARCHAR(255) NOT NULL,
    village_code BIGINT,
    CONSTRAINT fk_village_code FOREIGN KEY (village_code) REFERENCES village (code)
);