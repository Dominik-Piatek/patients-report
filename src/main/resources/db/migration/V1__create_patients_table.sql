CREATE TABLE patients
(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(20),
    pesel VARCHAR(11),
    city VARCHAR(100),
    registration_date DATE NOT NULL
);