CREATE TABLE customer
(
    id           UUID         NOT NULL,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    address      VARCHAR(255) NOT NULL,
    created_date DATE         NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

ALTER TABLE customer
    ADD CONSTRAINT "uc_customer_emaıl" UNIQUE (email);