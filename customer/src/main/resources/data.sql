CREATE TABLE IF NOT EXISTS customer
(
    id              UUID PRIMARY KEY,
    first_name      VARCHAR(255)        NOT NULL,
    last_name       VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    created_date    DATE                NOT NULL
);

CREATE TABLE IF NOT EXISTS address
(
    id              UUID PRIMARY KEY,
    customer_id     UUID                NOT NULL,
    city            VARCHAR(255)        NOT NULL,
    street          VARCHAR(255)        NOT NULL,
    house_number    VARCHAR(255)        NOT NULL,
    zip_code        VARCHAR(255)        NOT NULL,
    created_date    DATE                NOT NULL
);

INSERT INTO customer (id, first_name, last_name, email, created_date)
VALUES ('4257fc33-5c44-4216-96b7-dda39864f56f',
       'John',
       'Doe',
       'john.doe@example.com',
       '2026-01-15');

INSERT INTO address (id, customer_id, city, street, house_number, zip_code, created_date)
VALUES ('3c6dd77b-9ae6-4124-9152-74a1694bf71c',
        '4257fc33-5c44-4216-96b7-dda39864f56f',
        'Istanbul',
        'Zile',
        '24',
        '34000',
        '2026-01-15');