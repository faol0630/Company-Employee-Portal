

CREATE TABLE IF NOT EXISTS address (
    address_id int AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255),
    street VARCHAR(255),
    zipcode VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS company (
    company_id int AUTO_INCREMENT PRIMARY KEY,
    country VARCHAR(255),
    company_name VARCHAR(255),
    address_int_id int,
    FOREIGN KEY (address_int_id) REFERENCES address(address_id)
);

CREATE TABLE IF NOT EXISTS department (
    department_id int AUTO_INCREMENT PRIMARY KEY,
    deparment_name VARCHAR(255),
    company_company_id int,
    FOREIGN KEY (company_company_id) REFERENCES company(company_id)
);

CREATE TABLE IF NOT EXISTS role (
    id_role BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS employee (
    id_employee BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    address_int_id int,
    department_department_id int,
    FOREIGN KEY (address_int_id) REFERENCES address(address_id),
    FOREIGN KEY (department_department_id) REFERENCES department(department_id)
);

CREATE TABLE IF NOT EXISTS employee_seq (
    next_val BIGINT
);

CREATE TABLE IF NOT EXISTS employees_roles (
    id_employee BIGINT,
    id_role BIGINT
);

-- Inserción de un registro en la tabla address
INSERT INTO address (street, number, zipcode)
VALUES ('Vereda', '4', '67548');

-- Suponiendo que el address_id del registro insertado es 1
INSERT INTO company (company_id, country, company_name, address_int_id)
VALUES (248, 'Spain', 'Computers.S.A', 1);
