CREATE TABLE category
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    created_at      datetime NULL,
    last_updated_at datetime NULL,
    name            VARCHAR(255) NULL,
    is_deleted      BIT(1) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    created_at      datetime NULL,
    last_updated_at datetime NULL,
    description   VARCHAR(255) NULL,
    image_url       VARCHAR(255) NULL,
    price DOUBLE NOT NULL,
    title           VARCHAR(255) NULL,
    category_id     BIGINT NULL,
    is_deleted      BIT(1) NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);