-- Passwords are encoded using BCrypt.
-- 'user' password is 'password123'
-- 'admin' password is 'adminpass'
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$Y8TbV7yyv8pCz2OONmh3aerwTKbe/cNvBPtQs15qVCe4fSmTeLflq', TRUE);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$Y8TbV7yyv8pCz2OONmh3aerwTKbe/cNvBPtQs15qVCe4fSmTeLflq', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');