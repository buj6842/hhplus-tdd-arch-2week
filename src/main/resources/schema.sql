CREATE TABLE users (
user_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(255) NOT NULL
);

CREATE TABLE lecture (
lecture_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
lecture_title VARCHAR(255) NOT NULL,
description TEXT,
lecture_teacher VARCHAR(255) NOT NULL,
capacity INT NOT NULL DEFAULT 30,
lecture_date DATE NOT NULL
);

CREATE TABLE lecture_registration (
lecture_registration_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
user_seq BIGINT,
lecture_seq BIGINT,
registered_at TIMESTAMP
);
