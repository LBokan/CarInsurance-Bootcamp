-- liquibase formatted sql

-- changeset LizavetaBokan:4
DROP TABLE assignments CASCADE;
DROP TABLE vehicle_info CASCADE;

CREATE TABLE assignments (
    assignment_id SERIAL NOT NULL PRIMARY KEY,
    date_of_creation TIMESTAMP NOT NULL,
    date_of_incident DATE NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users (user_id)
);

-- VEHICLE INFO
CREATE TABLE vehicle_info (
    id SERIAL NOT NULL PRIMARY KEY,
    vin_number VARCHAR(50) NOT NULL,
    car_make VARCHAR(50) NOT NULL,
    car_model VARCHAR(50) NOT NULL,
    year_of_manufacture INTEGER NOT NULL,
    odometer_value VARCHAR(50) NOT NULL,
    license_plate_number VARCHAR(50) NOT NULL,
    license_state VARCHAR(50) NOT NULL,
    license_expiration_date DATE NOT NULL,
    assignment_id INTEGER NOT NULL REFERENCES assignments (assignment_id)
);

ALTER TABLE vehicle_info
ADD CONSTRAINT fk_assignment
FOREIGN KEY (assignment_id)
REFERENCES assignments (assignment_id);

INSERT INTO assignments (user_id, date_of_creation, date_of_incident)
VALUES
(1, '2023-06-10', '2023-05-13');

INSERT INTO vehicle_info (
    vin_number,
    car_make,
    car_model,
    year_of_manufacture,
    odometer_value,
    license_plate_number,
    license_state,
    license_expiration_date,
    assignment_id
)
VALUES
('11111111111111111', 'Audi1', 'A1', '2021', '10000', '1111111', 'CA', '2023-06-16', 1);
