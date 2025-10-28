-- ======================
-- USERS TABLE
-- ======================
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(10)
);

-- ======================
-- ROLES TABLE
-- ======================
CREATE TABLE roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL  -- 'organizer', 'attendee'
);

-- ======================
-- USER_ROLES (MAPPING TABLE)
-- Many-to-Many between users and roles
-- ======================
CREATE TABLE user_info_roles_details (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- ======================
-- CATEGORIES TABLE
-- ======================
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) UNIQUE NOT NULL,
    created_by INT NOT NULL,
    CONSTRAINT fk_category_user FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- ======================
-- EVENTS TABLE
-- ======================
CREATE TABLE event_details (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    organizer_id INT NOT NULL,
    event_title VARCHAR(100) NOT NULL,
    event_description CLOB,
    event_date DATE NOT NULL,
    start_time TIME,
    end_time TIME,
    location VARCHAR(255),
    capacity INT,
    available_seats INT,
    artists VARCHAR(255),
    age_limit INT,
    price DECIMAL(10,2),
    event_image VARCHAR(255),
    CONSTRAINT fk_event_category FOREIGN KEY (category_id) REFERENCES categories(category_id),
    CONSTRAINT fk_event_organizer FOREIGN KEY (organizer_id) REFERENCES users(user_id)
);

-- ======================
-- REGISTRATIONS TABLE
-- ======================
CREATE TABLE registrations (
    registration_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    event_id INT NOT NULL,
    total_tickets INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'Pending',
    total_price DECIMAL(10,2),
    CONSTRAINT fk_registration_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_registration_event FOREIGN KEY (event_id) REFERENCES event_details(event_id)
);
