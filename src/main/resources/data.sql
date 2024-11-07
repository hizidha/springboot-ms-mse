INSERT INTO application_user (full_name, email, username, password, profile_picture_path, created_at, updated_at)
VALUES
    ('Adisaputra Zidha', 'hi.zidha@gmail.com', 'hizidha', '$2a$12$qDYYxWYq4Jt533f/XhFXkeSWylcjuVxei.0bCXfTssNkyVO/Z1ySm', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Andi Wijaya', 'andi.wijaya@example.com', 'andiwijaya', '$2a$12$qDYYxWYq4Jt533f/XhFXkeSWylcjuVxei.0bCXfTssNkyVO/Z1ySm', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Rina Ayu', 'rina.ayu@example.com', 'rinaayu', '$2a$12$qDYYxWYq4Jt533f/XhFXkeSWylcjuVxei.0bCXfTssNkyVO/Z1ySm', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Budi Santoso', 'budi.santoso@example.com', 'budisantoso', '$2a$12$qDYYxWYq4Jt533f/XhFXkeSWylcjuVxei.0bCXfTssNkyVO/Z1ySm', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Citra Dewi', 'citra.dewi@example.com', 'citradewi', '$2a$12$qDYYxWYq4Jt533f/XhFXkeSWylcjuVxei.0bCXfTssNkyVO/Z1ySm', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Eko Prabowo', 'eko.prabowo@example.com', 'ekoprabowo', '$2a$12$qDYYxWYq4Jt533f/XhFXkeSWylcjuVxei.0bCXfTssNkyVO/Z1ySm', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Dewi Lestari', 'dewi.lestari@example.com', 'dewilestari', '$2a$12$qDYYxWYq4Jt533f/XhFXkeSWylcjuVxei.0bCXfTssNkyVO/Z1ySm', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO categories (name, created_at, updated_at)
VALUES
    ('Workshops', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Trainings', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Meetups', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Conferences', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tags (name, created_at, updated_at)
VALUES
    ('Human Resources', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Prototyping', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Business Development', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Web Development', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Machine Learning', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Data Science', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Artificial Intelligence', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Cybersecurity', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Mobile Development', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO events (title, description, interviewee, location, date, capacity, attendee_count, created_at, updated_at)
VALUES
    ('AI and Machine Learning Meetup', 'Discussing trends in AI and machine learning.', 'John Doe', 'Jakarta', '2024-11-25 10:00:00', 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Web Development Workshop', 'Hands-on workshop to build web applications.', 'Jane Smith', 'Bandung', '2024-12-01 09:00:00', 100, 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Cybersecurity Conference', 'Latest trends in cybersecurity and best practices.', 'Alex Turner', 'Yogyakarta', '2024-12-05 13:00:00', 200, 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Business Development Meetup', 'Networking event for business developers.', 'Mary Johnson', 'Surabaya', '2024-11-20 15:00:00', 30, 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Data Science Training', 'An introductory course on data science concepts and tools.', 'Michael Scott', 'Semarang', '2024-12-10 11:00:00', 40, 35, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO event_categories (category_id, event_id)
VALUES
    (1, 1),
    (2, 2),
    (2, 3),
    (3, 4),
    (3, 5),
    (4, 5);

INSERT INTO event_tags (tag_id, event_id)
VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 2),
    (4, 3),
    (1, 3),
    (2, 4),
    (3, 4),
    (3, 5),
    (5, 5);

INSERT INTO attendees (name, email, phone, event_id, registered_on)
VALUES
    ('Siti Aminah', 'siti.aminah@example.com', '081234567890', 1, CURRENT_TIMESTAMP),
    ('Rudi Hartono', 'rudi.hartono@example.com', '081234567891', 1, CURRENT_TIMESTAMP),
    ('Maria Tan', 'maria.tan@example.com', '081234567892', 1, CURRENT_TIMESTAMP),
    ('Ali Rahman', 'ali.rahman@example.com', '081234567893', 2, CURRENT_TIMESTAMP),
    ('Nina Surya', 'nina.surya@example.com', '081234567894', 2, CURRENT_TIMESTAMP),
    ('Dimas Kurnia', 'dimas.kurnia@example.com', '081234567895', 2, CURRENT_TIMESTAMP),
    ('Bunga Anggraeni', 'bunga.anggraeni@example.com', '081234567896', 3, CURRENT_TIMESTAMP),
    ('Bayu Prasetyo', 'bayu.prasetyo@example.com', '081234567897', 3, CURRENT_TIMESTAMP),
    ('Eka Wulandari', 'eka.wulandari@example.com', '081234567898', 3, CURRENT_TIMESTAMP),
    ('Adi Nugroho', 'adi.nugroho@example.com', '081234567899', 4, CURRENT_TIMESTAMP),
    ('Sri Maharani', 'sri.maharani@example.com', '081234567800', 4, CURRENT_TIMESTAMP),
    ('Anton Saputra', 'anton.saputra@example.com', '081234567801', 4, CURRENT_TIMESTAMP),
    ('Rina Ayu', 'rina.ayu@example.com', '081234567802', 5, CURRENT_TIMESTAMP),
    ('Joko Widodo', 'joko.widodo@example.com', '081234567803', 5, CURRENT_TIMESTAMP),
    ('Dewi Ayu', 'dewi.ayu@example.com', '081234567804', 5, CURRENT_TIMESTAMP);