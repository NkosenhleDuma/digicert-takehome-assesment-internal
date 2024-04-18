CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL,

    customer_email VARCHAR(255),

    num_adults INT,
    num_children INT,

    start_date DATE,
    end_date DATE,

    num_beds INT,

    notes TEXT,

    create_date DATE DEFAULT CURRENT_TIMESTAMP,
    modified_date DATE DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);