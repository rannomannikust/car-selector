CREATE TABLE car_brands (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    CONSTRAINT fk_car_brand_parent FOREIGN KEY (parent_id) REFERENCES car_brands(id)
);
