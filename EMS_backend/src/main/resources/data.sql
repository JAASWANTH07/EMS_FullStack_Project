-- ======================
-- ROLES
-- ======================
INSERT INTO roles (role_name) VALUES ('Organizer');
INSERT INTO roles (role_name) VALUES ('Participant');
-- ======================
-- USERS
-- ======================
INSERT INTO users (user_name, email, password, phone) VALUES ('Alice', 'alice@ems.com', '$2a$10$pr1.og0lY8ojh73ULvhzC.46P9U8bjvZLBlUHEtzLSdjHw6O6RVIK', '9876543210');
INSERT INTO users (user_name, email, password, phone) VALUES ('Bob', 'bob@ems.com', '$2a$10$pr1.og0lY8ojh73ULvhzC.46P9U8bjvZLBlUHEtzLSdjHw6O6RVIK', '9123456780');
INSERT INTO users (user_name, email, password, phone) VALUES ('Charlie', 'charlie@ems.com', '$2a$10$pr1.og0lY8ojh73ULvhzC.46P9U8bjvZLBlUHEtzLSdjHw6O6RVIK', '9988776655');

-- ======================
-- USER_ROLES
-- ======================
-- Alice is an organizer
INSERT INTO user_info_roles_details (user_id, role_id) VALUES
(1, 1);

-- Bob is an attendee
INSERT INTO user_info_roles_details (user_id, role_id) VALUES
(2, 2);

-- Charlie is an organizer
INSERT INTO user_info_roles_details (user_id, role_id) VALUES
(3, 1);

-- ======================
-- CATEGORIES
-- ======================
INSERT INTO categories (category_name, created_by) VALUES ('Music', 1);
INSERT INTO categories (category_name, created_by) VALUES ('Sports', 1);
INSERT INTO categories (category_name, created_by) VALUES ('Tech', 3);

-- ======================
-- EVENTS
-- ======================
INSERT INTO event_details (category_id, organizer_id, event_title, event_description, event_date, start_time, end_time, location, capacity, available_seats, artists, age_limit, price, event_image)
VALUES (1, 1, 'Rock Concert', 'Live rock concert featuring top bands', '2025-11-10', '18:00:00', '22:00:00', 'City Arena', 100, 98, 'Band A, Band B', 18, 50.00, 'https://plus.unsplash.com/premium_photo-1708589334678-1499e081707d?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1632');

INSERT INTO event_details (category_id, organizer_id, event_title, event_description, event_date, start_time, end_time, location, capacity, available_seats, artists, age_limit, price, event_image)
VALUES (2, 1, 'Marathon', 'City-wide marathon event', '2025-12-05', '06:00:00', '12:00:00', 'Central Park', 200, 199, '', 16, 10.00, 'https://images.unsplash.com/photo-1524646349956-1590eacfa324?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1470');

INSERT INTO event_details (category_id, organizer_id, event_title, event_description, event_date, start_time, end_time, location, capacity, available_seats, artists, age_limit, price, event_image)
VALUES (3, 3, 'Tech Conference', 'Annual technology conference', '2025-11-20', '09:00:00', '17:00:00', 'Tech Convention Center', 150, 149, 'Guest Speakers', 16, 100.00, 'https://images.unsplash.com/photo-1504384764586-bb4cdc1707b0?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1470');

-- ======================
-- REGISTRATIONS
-- ======================
INSERT INTO registrations (user_id, event_id, total_tickets, status, total_price)
VALUES (2, 1, 2,'confirmed', 100.00);

INSERT INTO registrations (user_id, event_id, total_tickets, status, total_price)
VALUES (2, 2, 1,'confirmed', 10.00);

INSERT INTO registrations (user_id, event_id, total_tickets, status, total_price)
VALUES (2, 3, 1,'pending', 100.00);
