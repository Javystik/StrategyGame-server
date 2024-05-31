INSERT INTO unit_type(name, size)
VALUES ('Королівський', 250),
       ('Дальнобійний', 120),
       ('Ближньобійний', 400);

INSERT INTO unit_template(unit_type_id, name, cost, reload, health_points, damage, speed, range)
VALUES (1, 'Королева', 99999, 0, 500, 0, 5, 0),
       (2, 'Лучник', 10, 2, 100, 50, 12, 800),
       (2, 'Арбалетчик', 20, 4, 75, 75, 10, 1000),
       (2, 'Снайпер', 30, 6, 25, 125, 8, 1400),
       (3, 'Танк', 10, 0, 200, 0, 15, 0);