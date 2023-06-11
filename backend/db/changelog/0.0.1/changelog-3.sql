-- liquibase formatted sql

-- changeset LizavetaBokan:3
CREATE TABLE assignments (
    assignment_id SERIAL NOT NULL PRIMARY KEY,
    date_of_creation TIMESTAMP NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users (user_id)
);

-- CONTACTS INFO
CREATE TABLE contacts_info (
    id SERIAL NOT NULL PRIMARY KEY,
    date_of_creation TIMESTAMP NOT NULL,
    type VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    assignment_id INTEGER NOT NULL REFERENCES assignments (assignment_id)
);

ALTER TABLE contacts_info
ADD CONSTRAINT fk_assignment
FOREIGN KEY (assignment_id)
REFERENCES assignments (assignment_id);

CREATE TABLE phone_numbers (
    id SERIAL NOT NULL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    number VARCHAR(50) NOT NULL,
    contacts_info_id INTEGER NOT NULL REFERENCES contacts_info (id)
);

ALTER TABLE phone_numbers
ADD CONSTRAINT fk_contact
FOREIGN KEY (contacts_info_id)
REFERENCES contacts_info (id);

CREATE TABLE addresses (
    id SERIAL NOT NULL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip VARCHAR(50) NOT NULL,
    address_line VARCHAR(255) NOT NULL,
    contacts_info_id INTEGER NOT NULL REFERENCES contacts_info (id)
);

ALTER TABLE addresses
ADD CONSTRAINT fk_contact
FOREIGN KEY (contacts_info_id)
REFERENCES contacts_info (id);

-- VEHICLE INFO
CREATE TABLE vehicle_info (
    id SERIAL NOT NULL PRIMARY KEY,
    vin_number VARCHAR(50) NOT NULL,
    car_make VARCHAR(50) NOT NULL,
    car_model VARCHAR(50) NOT NULL,
    year_of_manufacture VARCHAR(50) NOT NULL,
    odometer_value VARCHAR(50) NOT NULL,
    license_plate_number VARCHAR(50) NOT NULL,
    license_state VARCHAR(50) NOT NULL,
    license_expiration_date VARCHAR(50) NOT NULL,
    assignment_id INTEGER NOT NULL REFERENCES assignments (assignment_id)
);

ALTER TABLE vehicle_info
ADD CONSTRAINT fk_assignment
FOREIGN KEY (assignment_id)
REFERENCES assignments (assignment_id);

-- VEHICLE CONDITION INFO
CREATE TABLE directions_of_impact (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE vehicle_condition_info (
    id SERIAL NOT NULL PRIMARY KEY,
    assignment_id INTEGER NOT NULL REFERENCES assignments (assignment_id)
);

ALTER TABLE vehicle_condition_info
ADD CONSTRAINT fk_assignment
FOREIGN KEY (assignment_id)
REFERENCES assignments (assignment_id);

CREATE TABLE directions_of_impact_vehicle_condition_info (
    id SERIAL NOT NULL PRIMARY KEY,
    direction_of_impact_id INTEGER NOT NULL REFERENCES directions_of_impact (id),
    vehicle_condition_info_id INTEGER NOT NULL REFERENCES vehicle_condition_info (id)
);

ALTER TABLE directions_of_impact_vehicle_condition_info
ADD CONSTRAINT fk_impacts
FOREIGN KEY (direction_of_impact_id)
REFERENCES directions_of_impact (id);

ALTER TABLE directions_of_impact_vehicle_condition_info
ADD CONSTRAINT fk_condition
FOREIGN KEY (vehicle_condition_info_id)
REFERENCES vehicle_condition_info (id);

INSERT INTO assignments (user_id, date_of_creation)
VALUES
(1, '2023-06-10T10:30:45.123456789');

INSERT INTO contacts_info (date_of_creation, type, first_name, last_name, email, assignment_id)
VALUES
('2023-06-10T11:30:45.123456789', 'Vehicle owner', 'Test1', 'Testikovich1', 'test1@test.com', 1),
('2023-06-10T11:35:00.123456789', 'Vehicle driver', 'Test2', 'Testikovich2', 'test2@test.com', 1);

INSERT INTO phone_numbers (type, number, contacts_info_id)
VALUES
('Mobile', '111111111', 1),
('Fax', '2222222222', 1),
('Fax', '333333333', 1),
('Fax', '444444444', 2);

INSERT INTO addresses (type, city, state, zip, address_line, contacts_info_id)
VALUES
('Home', 'City1', 'State1', '11111', 'AddressLine1', 1),
('Work', 'City2', 'State2', '22222', 'AddressLine2', 1),
('Home', 'City3', 'State3', '3333', 'AddressLine3', 2);

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
('11111111111111111', 'Audi1', 'A1', '2021', '10000', '1111111', 'CA', '16/06/2023', 1);

INSERT INTO directions_of_impact (name)
VALUES
('Front'), ('Front Right'), ('Right Side'), ('Right Quarter Panel'), ('Right Rear'),
('Rear'), ('Front Left'), ('Left Side'), ('Left Quarter Panel'), ('Left Rear');

INSERT INTO vehicle_condition_info (assignment_id)
VALUES
(1);

INSERT INTO directions_of_impact_vehicle_condition_info (direction_of_impact_id, vehicle_condition_info_id)
VALUES
(1, 1),
(3, 1),
(4, 1),
(5, 1),
(8, 1),
(9, 1);
