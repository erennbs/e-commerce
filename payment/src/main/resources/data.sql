CREATE TABLE IF NOT EXISTS payment
(
    id             UUID                        NOT NULL,
    amount         DECIMAL,
    payment_method VARCHAR(255),
    order_id       UUID,
    created_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_date   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);