CREATE TABLE customer
(
    customer_id         UUID DEFAULT gen_random_uuid(),
    customer_name       VARCHAR,
    customer_created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_customer_id PRIMARY KEY (customer_id)
);