CREATE TABLE IF NOT EXISTS customer_order
(
    id             UUID                        NOT NULL,
    reference      VARCHAR(255)                NOT NULL,
    total_amount   DECIMAL                     NOT NULL,
    payment_method VARCHAR(255),
    customer_id    UUID                        NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_customer_order PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_line
(
    id         UUID             NOT NULL,
    order_id   UUID,
    product_id UUID             NOT NULL,
    quantity   DOUBLE PRECISION NOT NULL,
    CONSTRAINT "pk_orderline" PRIMARY KEY (id)
);