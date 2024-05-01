CREATE TABLE unit_type
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(30)
);
CREATE TABLE unit
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    name          Varchar(30) NOT NULL,
    unit_type_id  INTEGER     not null,
    health_points int         NOT NULL,
    damage        int         NOT NULL,
    speed         int         NOT NULL,
    range         int         NOT NULL,
    level         int         NOT NULL,
    FOREIGN KEY (unit_type_id) REFERENCES unit_type (id)
);
