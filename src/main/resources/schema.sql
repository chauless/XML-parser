CREATE TABLE village
(
    code BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE village_part
(
    code         BIGINT PRIMARY KEY,
    name         VARCHAR(255),
    village_code BIGINT,
    FOREIGN KEY (village_code) REFERENCES village (code)
);