CREATE TABLE clients(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    fathername VARCHAR(50) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    email VARCHAR(20) NOT NULL
);
CREATE TABLE realties(
    id SERIAL PRIMARY KEY,
    neighbourhood VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    square decimal,
    room_number SMALLSERIAL,
    price decimal,
    cadastral_number VARCHAR(20)
);
CREATE TABLE requests(
    id SERIAL PRIMARY KEY,
    purpose VARCHAR(4) NOT NULL,
    client_id SERIAL,
    realty_id SERIAL,
    dt timestamp,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (realty_id) REFERENCES realties(id) ON DELETE SET NULL
);
CREATE TABLE deals(
    id SERIAL PRIMARY KEY,
    seller SERIAL,
    buyer SERIAL,
    realty SERIAL,
    dt timestamp,
    FOREIGN KEY (seller) REFERENCES clients(id) ON DELETE RESTRICT,
    FOREIGN KEY (buyer) REFERENCES clients(id) ON DELETE RESTRICT,
    FOREIGN KEY (realty) REFERENCES realties(id) ON DELETE RESTRICT
)