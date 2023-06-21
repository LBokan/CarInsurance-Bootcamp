-- liquibase formatted sql

-- changeset LizavetaBokan:5
DROP TABLE assignments CASCADE;

CREATE TABLE assignment_statuses (
    status_id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE assignments (
    assignment_id SERIAL NOT NULL PRIMARY KEY,
    date_of_creation TIMESTAMP NOT NULL,
    date_of_incident DATE NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users (user_id),
    status_id INTEGER NOT NULL REFERENCES assignment_statuses (status_id)
);

ALTER TABLE assignments
ADD CONSTRAINT fk_status
FOREIGN KEY (status_id)
REFERENCES assignment_statuses (status_id);

INSERT INTO assignment_statuses (name)
VALUES
('NEW'),
('IN_PROGRESS'),
('ASSIGNED'),
('IN_REVIEW'),
('TOTAL_LOSS'),
('REPAIR'),
('REPAIRED');

INSERT INTO assignments (user_id, status_id, date_of_creation, date_of_incident)
VALUES
(1, 1, '2023-06-10', '2023-05-13');
