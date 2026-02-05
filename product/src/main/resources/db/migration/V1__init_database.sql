CREATE TABLE category
(
    id              UUID NOT NULL,
    name            VARCHAR(255) NOT NULL,
    description     VARCHAR(255) NOT NULL,
    created_date    DATE NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id                 UUID NOT NULL,
    name               VARCHAR(255) NOT NULL,
    description        VARCHAR(255),
    available_quantity DOUBLE PRECISION NOT NULL,
    price              DECIMAL NOT NULL,
    category_id        UUID NOT NULL,
    created_date       DATE NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);