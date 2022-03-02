CREATE TABLE position(
                         id SERIAL PRIMARY KEY,
                         position VARCHAR(255)
);

CREATE TABLE status(
                       id SERIAL PRIMARY KEY,
                       status VARCHAR(255)
);

CREATE TABLE coordinates(
                            id SERIAL PRIMARY KEY,
                            x_coord NUMERIC,
                            y_coord NUMERIC NOT NULL
);

CREATE TABLE location(
                         id SERIAL PRIMARY KEY,
                         x_coord NUMERIC NOT NULL,
                         y_coord INTEGER NOT NULL,
                         name VARCHAR(700)
);

CREATE TABLE address(
                        id SERIAL PRIMARY KEY,
                        zip_code VARCHAR(255),
                        location_id INTEGER REFERENCES location(id)
);
CREATE TABLE organization(
                             id SERIAL PRIMARY KEY,
                             annual_turnover NUMERIC,
                             employees_count BIGINT,
                             address_id INTEGER NOT NULL REFERENCES address(id)
);

CREATE TABLE worker(
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       coordinates_id INTEGER NOT NULL REFERENCES coordinates(id),
                       creationDate TIMESTAMP NOT NULL,
                       salary NUMERIC NOT NULL,
                       startDate TIMESTAMP NOT NULL,
                       position_id INTEGER REFERENCES position(id),
                       status_id INTEGER REFERENCES status(id),
                       organization_id INTEGER REFERENCES organization(id)
);
