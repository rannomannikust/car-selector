CREATE TABLE user_selections (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    has_license BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE user_selection_car_brands (
    user_selection_id BIGINT NOT NULL,
    car_brand_id BIGINT NOT NULL,
    PRIMARY KEY (user_selection_id, car_brand_id),
    CONSTRAINT fk_user_selection FOREIGN KEY (user_selection_id) REFERENCES user_selections(id) ON DELETE CASCADE,
    CONSTRAINT fk_car_brand FOREIGN KEY (car_brand_id) REFERENCES car_brands(id)
);