CREATE TABLE IF NOT EXISTS customer
(
    id              UUID PRIMARY KEY,
    first_name      VARCHAR(255)        NOT NULL,
    last_name       VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    address         VARCHAR(255)        NOT NULL,
    created_date    DATE                NOT NULL
);

INSERT INTO customer (id, first_name, last_name, email, address, created_date)
VALUES ('4257fc33-5c44-4216-96b7-dda39864f56f',
       'John',
       'Doe',
       'john.doe@example.com',
       'Lombard Street, CA, San Francisco' ,
       '2026-01-15');
