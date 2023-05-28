-- liquibase formatted sql

-- changeset LizavetaBokan:2
DROP TABLE roles CASCADE;
DROP TABLE users CASCADE;

CREATE TABLE roles (
    role_id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    user_id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(68) NOT NULL,
    role_id INTEGER NOT NULL REFERENCES roles (role_id)
);

ALTER TABLE users
ADD CONSTRAINT fk_role
FOREIGN KEY (role_id)
REFERENCES roles (role_id);

INSERT INTO roles (name)
VALUES
('ROLE_CLIENT'),
('ROLE_ESTIMATOR'),
('ROLE_ADMIN');

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUES
('User1', 'Smith1', 'user1@test.com', '$2a$10$3OI6s.FQTIJ604gB3p6yv.Q6S9X.zgtQIsyrvFXnLsqrkEhvqyNnu', 1),
('User2', 'Smith2', 'user2@test.com', '$2a$10$3OI6s.FQTIJ604gB3p6yv.Q6S9X.zgtQIsyrvFXnLsqrkEhvqyNnu', 1),
('User3', 'Smith3', 'user3@test.com', '$2a$10$3OI6s.FQTIJ604gB3p6yv.Q6S9X.zgtQIsyrvFXnLsqrkEhvqyNnu', 1),
('Estimator1', 'Petrov1', 'estimator1@test.com', '$2a$10$3OI6s.FQTIJ604gB3p6yv.Q6S9X.zgtQIsyrvFXnLsqrkEhvqyNnu', 2),
('Estimator2', 'Petrov2', 'estimator2@test.com', '$2a$10$3OI6s.FQTIJ604gB3p6yv.Q6S9X.zgtQIsyrvFXnLsqrkEhvqyNnu', 2),
('Admin', 'Ivanov', 'admin@test.com', '$2a$10$3OI6s.FQTIJ604gB3p6yv.Q6S9X.zgtQIsyrvFXnLsqrkEhvqyNnu', 3);