CREATE TABLE vehicle
(
    vehicle_id         UUID DEFAULT gen_random_uuid(),
    vehicle_category   VARCHAR,
    vehicle_created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_vehicle_id PRIMARY KEY (vehicle_id)
);