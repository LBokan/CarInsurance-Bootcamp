-- liquibase formatted sql

-- changeset LizavetaBokan:1
CREATE TABLE roles (
    role_id SERIAL NOT NULL PRIMARY KEY,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE users (
    user_id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password CHAR(68) NOT NULL,
    role_id INTEGER NOT NULL REFERENCES roles (role_id)
);

INSERT INTO roles (role)
VALUES
('ROLE_CLIENT'),
('ROLE_ESTIMATOR'),
('ROLE_ADMIN');

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUES
('User1', 'Smith1', 'user1@test.com', '{bcrypt}$2a$10$RptdQik/ljYtlkEKR3L9su8j6bf27mFdjmBA5z5bn2amAH4fVyaee', 1),
('User2', 'Smith2', 'user2@test.com', '{bcrypt}$2a$10$RptdQik/ljYtlkEKR3L9su8j6bf27mFdjmBA5z5bn2amAH4fVyaee', 1),
('User3', 'Smith3', 'user3@test.com', '{bcrypt}$2a$10$RptdQik/ljYtlkEKR3L9su8j6bf27mFdjmBA5z5bn2amAH4fVyaee', 1),
('Estimator1', 'Petrov1', 'estimator1@test.com', '{bcrypt}$2a$10$RptdQik/ljYtlkEKR3L9su8j6bf27mFdjmBA5z5bn2amAH4fVyaee', 2),
('Estimator2', 'Petrov2', 'estimator2@test.com', '{bcrypt}$2a$10$RptdQik/ljYtlkEKR3L9su8j6bf27mFdjmBA5z5bn2amAH4fVyaee', 2),
('Admin', 'Ivanov', 'admin@test.com', '{bcrypt}$2a$10$RptdQik/ljYtlkEKR3L9su8j6bf27mFdjmBA5z5bn2amAH4fVyaee', 3);