CREATE TABLE products (
    id VARCHAR PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL,
    price NUMERIC(19,2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE orders (
    id VARCHAR PRIMARY KEY NOT NULL,
    user_id VARCHAR NOT NULL,
    total_price NUMERIC(19,2) NOT NULL,
    status VARCHAR NOT NULL
);

CREATE TABLE order_items (
    id VARCHAR PRIMARY KEY NOT NULL,
    order_id VARCHAR NOT NULL,
    product_id VARCHAR NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(19,2) NOT NULL
);