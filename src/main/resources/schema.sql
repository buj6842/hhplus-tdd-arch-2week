CREATE TABLE users (
                       user_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL
);

CREATE TABLE lecture (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         description TEXT,
                         lecturer VARCHAR(255) NOT NULL,
                         capacity INT NOT NULL DEFAULT 30,
                         lecture_date DATE NOT NULL
);

CREATE TABLE lecture_registration (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      user_id BIGINT,
                                      lecture_id BIGINT,
                                      registered_at TIMESTAMP
);
