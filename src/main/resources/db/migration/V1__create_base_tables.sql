CREATE TABLE users
(
    id                   INTEGER PRIMARY KEY AUTOINCREMENT,
    username             VARCHAR(30) NOT NULL,
    password             TEXT        NOT NULL,
    email                VARCHAR(60) NOT NULL,
    alliance_id          INTEGER              DEFAULT NULL,
    created_at           DATE        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    player_games         INTEGER     NOT NULL DEFAULT 0,
    win_games            INTEGER     NOT NULL DEFAULT 0,
    enemy_units_killed   INTEGER     NOT NULL DEFAULT 0,
    units_deaths         INTEGER     NOT NULL DEFAULT 0,
    territories_captured INTEGER     NOT NULL DEFAULT 0,
    territories_lost     INTEGER     NOT NULL DEFAULT 0
);

CREATE TABLE article
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        VARCHAR(120) NOT NULL,
    description TEXT         NOT NULL,
    user_id     INTEGER      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE alliance
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    name          VARCHAR(30) NOT NULL,
    members_count INTEGER     NOT NULL,
    total_wins    INTEGER     NOT NULL
);
