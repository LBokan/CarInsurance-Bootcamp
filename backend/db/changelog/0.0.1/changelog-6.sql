-- liquibase formatted sql

-- changeset LizavetaBokan:6
DROP TABLE roles CASCADE;
DROP TABLE users CASCADE;
DROP TABLE assignment_statuses CASCADE;
DROP TABLE assignments CASCADE;
DROP TABLE contacts_info CASCADE;
DROP TABLE phone_numbers CASCADE;
DROP TABLE addresses CASCADE;
DROP TABLE vehicle_info CASCADE;
DROP TABLE vehicle_condition_info CASCADE;

CREATE TABLE roles (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE company_types (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE companies (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    type_id INTEGER NOT NULL REFERENCES company_types (id)
);

ALTER TABLE companies
ADD CONSTRAINT fk_type
FOREIGN KEY (type_id)
REFERENCES company_types (id);

CREATE TABLE users (
    id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(68) NOT NULL,
    role_id INTEGER NOT NULL REFERENCES roles (id),
    company_id INTEGER NOT NULL REFERENCES companies (id)
);

ALTER TABLE users
ADD CONSTRAINT fk_role
FOREIGN KEY (role_id)
REFERENCES roles (id);

ALTER TABLE users
ADD CONSTRAINT fk_company
FOREIGN KEY (company_id)
REFERENCES companies (id);

CREATE TABLE assignment_statuses (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE assignments (
    id SERIAL NOT NULL PRIMARY KEY,
    date_of_creation TIMESTAMP NOT NULL,
    date_of_incident DATE NOT NULL,
    status_id INTEGER NOT NULL REFERENCES assignment_statuses (id),
    insurance_agency_id INTEGER NOT NULL REFERENCES companies (id),
    repair_facility_id INTEGER REFERENCES companies (id),
    user_id INTEGER NOT NULL REFERENCES users (id)
);

ALTER TABLE assignments
ADD CONSTRAINT fk_status
FOREIGN KEY (status_id)
REFERENCES assignment_statuses (id);

ALTER TABLE assignments
ADD CONSTRAINT fk_insurance
FOREIGN KEY (insurance_agency_id)
REFERENCES companies (id);

ALTER TABLE assignments
ADD CONSTRAINT fk_repair
FOREIGN KEY (repair_facility_id)
REFERENCES companies (id);

ALTER TABLE assignments
ADD CONSTRAINT fk_user
FOREIGN KEY (user_id)
REFERENCES users (id);

CREATE TABLE contact_info_types (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE contacts_info (
    id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    type_id INTEGER NOT NULL REFERENCES contact_info_types (id),
    assignment_id INTEGER NOT NULL REFERENCES assignments (id)
);

ALTER TABLE contacts_info
ADD CONSTRAINT fk_type
FOREIGN KEY (type_id)
REFERENCES contact_info_types (id);

ALTER TABLE contacts_info
ADD CONSTRAINT fk_assignment
FOREIGN KEY (assignment_id)
REFERENCES assignments (id);

CREATE TABLE phone_number_types (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE phone_numbers (
    id SERIAL NOT NULL PRIMARY KEY,
    number VARCHAR(50) NOT NULL,
    type_id INTEGER NOT NULL REFERENCES phone_number_types (id),
    contact_info_id INTEGER NOT NULL REFERENCES contacts_info (id)
);

ALTER TABLE phone_numbers
ADD CONSTRAINT fk_type
FOREIGN KEY (type_id)
REFERENCES phone_number_types (id);

ALTER TABLE phone_numbers
ADD CONSTRAINT fk_contact
FOREIGN KEY (contact_info_id)
REFERENCES contacts_info (id);

CREATE TABLE address_types (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE addresses (
    id SERIAL NOT NULL PRIMARY KEY,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip VARCHAR(50) NOT NULL,
    address_line VARCHAR(255) NOT NULL,
    type_id INTEGER NOT NULL REFERENCES address_types (id),
    contact_info_id INTEGER NOT NULL REFERENCES contacts_info (id)
);

ALTER TABLE addresses
ADD CONSTRAINT fk_type
FOREIGN KEY (type_id)
REFERENCES address_types (id);

ALTER TABLE addresses
ADD CONSTRAINT fk_contact
FOREIGN KEY (contact_info_id)
REFERENCES contacts_info (id);

CREATE TABLE vehicle_info (
    id SERIAL NOT NULL PRIMARY KEY,
    vin_number VARCHAR(50) NOT NULL,
    car_make VARCHAR(50) NOT NULL,
    car_model VARCHAR(50) NOT NULL,
    year_of_manufacture INTEGER NOT NULL,
    odometer_value INTEGER NOT NULL,
    license_plate_number VARCHAR(50) NOT NULL,
    license_state VARCHAR(50) NOT NULL,
    license_expiration_date DATE NOT NULL,
    assignment_id INTEGER NOT NULL REFERENCES assignments (id)
);

ALTER TABLE vehicle_info
ADD CONSTRAINT fk_assignment
FOREIGN KEY (assignment_id)
REFERENCES assignments (id);

CREATE TABLE vehicle_condition_info (
    id SERIAL NOT NULL PRIMARY KEY,
    names_of_photos_of_impact VARCHAR(255) NOT NULL,
    direction_of_impact_id INTEGER NOT NULL REFERENCES directions_of_impact (id),
    assignment_id INTEGER NOT NULL REFERENCES assignments (id)
);

ALTER TABLE vehicle_condition_info
ADD CONSTRAINT fk_impact
FOREIGN KEY (direction_of_impact_id)
REFERENCES directions_of_impact (id);

ALTER TABLE vehicle_condition_info
ADD CONSTRAINT fk_assignment
FOREIGN KEY (assignment_id)
REFERENCES assignments (id);

CREATE TABLE comments (
    id SERIAL NOT NULL PRIMARY KEY,
    date_of_creation TIMESTAMP NOT NULL,
    text VARCHAR(255) NOT NULL,
    is_read INTEGER NOT NULL,
    assignment_id INTEGER NOT NULL REFERENCES assignments (id)
);

ALTER TABLE comments
ADD CONSTRAINT fk_assignment
FOREIGN KEY (assignment_id)
REFERENCES assignments (id);

INSERT INTO roles (name)
VALUES
('ROLE_CLIENT'),
('ROLE_INSURANCE_MANAGER'),
('ROLE_REPAIR_MANAGER');

INSERT INTO company_types (name)
VALUES
('INSURANCE_AGENCY'),
('REPAIR_FACILITY');

INSERT INTO companies (name, type_id)
VALUES
('Insure your car', 1),
('Be insured', 1),
('Safe your property', 1),
('Fixiki', 2),
('Repair center', 2),
('Engine power', 2),
('Luxury repair', 2),
('Best systems', 2);

INSERT INTO users (first_name, last_name, email, password, role_id, company_id)
VALUES
('Client1', 'Smith1', 'client1@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 1, 2),
('Client2', 'Smith2', 'client2@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 1, 3),
('Client3', 'Smith3', 'client3@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 1, 1),
('Client4', 'Smith4', 'client4@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 1, 3),
('InsuranceManager1', 'Petrov1', 'insurance1@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 2, 1),
('InsuranceManager2', 'Petrov2', 'insurance2@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 2, 1),
('InsuranceManager3', 'Petrov3', 'insurance3@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 2, 2),
('InsuranceManager4', 'Petrov4', 'insurance4@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 2, 3),
('InsuranceManager5', 'Petrov5', 'insurance5@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 2, 3),
('RepairManager1', 'Ivanov1', 'repair1@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 3, 4),
('RepairManager2', 'Ivanov2', 'repair2@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 3, 5),
('RepairManager3', 'Ivanov3', 'repair3@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 3, 6),
('RepairManager4', 'Ivanov4', 'repair4@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 3, 5),
('RepairManager5', 'Ivanov5', 'repair5@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 3, 8),
('RepairManager6', 'Ivanov6', 'repair6@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 3, 7),
('RepairManager7', 'Ivanov7', 'repair7@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 3, 4),
('RepairManager8', 'Ivanov8', 'repair8@test.com', '$2a$10$/GpMcmqG5y7WQjnNppyIuOI38K/79VzdISbyvOd76L6YYrGxE6qDG', 3, 6);

INSERT INTO assignment_statuses (name)
VALUES
('NEW'),
('IN_PROGRESS'),
('ASSIGNED'),
('IN_REVIEW'),
('TOTAL_LOSS'),
('REPAIR'),
('REPAIRED');

INSERT INTO assignments (date_of_creation, date_of_incident, user_id, status_id, insurance_agency_id)
VALUES
('2023-06-10', '2023-05-13', 1, 1, 1);

INSERT INTO contact_info_types (name)
VALUES
('Vehicle owner'),
('Vehicle driver'),
('Police'),
('Attorney'),
('Rental manager');

INSERT INTO contacts_info (first_name, last_name, email, assignment_id, type_id)
VALUES
('Test1', 'Testikovich1', 'test1@test.com', 1, 1),
('Test2', 'Testikovich2', 'test2@test.com', 1, 2);

INSERT INTO phone_number_types (name)
VALUES
('Home'),
('Business'),
('Mobile'),
('Fax'),
('Other');

INSERT INTO phone_numbers (number, contact_info_id, type_id)
VALUES
('111111111', 1, 3),
('2222222222', 1, 4),
('333333333', 1, 4),
('444444444', 2, 4);

INSERT INTO address_types (name)
VALUES
('Home'),
('Work'),
('Mailing'),
('Other');

INSERT INTO addresses (city, state, zip, address_line, contact_info_id, type_id)
VALUES
('City1', 'State1', '11111', 'AddressLine1', 1, 1),
('City2', 'State2', '22222', 'AddressLine2', 1, 2),
('City3', 'State3', '3333', 'AddressLine3', 2, 1);

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
('11111111111111111', 'Audi1', 'A1', 2021, 10000, '1111111', 'CA', '2023-06-16', 1);

INSERT INTO vehicle_condition_info (names_of_photos_of_impact, direction_of_impact_id, assignment_id)
VALUES
('photo1.png;photo2.jpg', 2 , 1);