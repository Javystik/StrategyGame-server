CREATE TABLE roles
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(20)
);
CREATE TABLE user_role
(
    user_id INTEGER,
    role_id INTEGER,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);
INSERT INTO roles(name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');