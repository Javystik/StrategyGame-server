DROP TABLE users;

CREATE TABLE users
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    username     VARCHAR(30) NOT NULL,
    password     TEXT        NOT NULL,
    email        VARCHAR(60) NOT NULL,
    created_at   DATE        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    alliance_id  INTEGER              DEFAULT NULL,
    statistic_id INTEGER              DEFAULT NULL,
    FOREIGN KEY (statistic_id) REFERENCES statistic (id)
);
CREATE TABLE statistic
(
    id                   INTEGER PRIMARY KEY AUTOINCREMENT,
    player_games         INTEGER NOT NULL DEFAULT 0,
    win_games            INTEGER NOT NULL DEFAULT 0,
    enemy_units_killed   INTEGER NOT NULL DEFAULT 0,
    units_deaths         INTEGER NOT NULL DEFAULT 0,
    territories_captured INTEGER NOT NULL DEFAULT 0,
    territories_lost     INTEGER NOT NULL DEFAULT 0
);