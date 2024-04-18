CREATE TABLE Bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL,

    customerFirstName VARCHAR(255),
    customerLastName VARCHAR(255),
    customerEmail VARCHAR(255),

    numAdults INT,
    numChildren INT,

    startDate DATE,
    endDate DATE,

    numBeds INT,

    notes TEXT
);