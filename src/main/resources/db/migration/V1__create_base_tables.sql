CREATE TABLE statistic
(
    id                   SERIAL PRIMARY KEY,
    player_games         INT DEFAULT 0,
    win_games            INT DEFAULT 0,
    enemy_units_killed   INT DEFAULT 0,
    units_deaths         INT DEFAULT 0,
    territories_captured INT DEFAULT 0,
    territories_lost     INT DEFAULT 0
);
CREATE TABLE users
(
    id                SERIAL PRIMARY KEY,
    username          VARCHAR(30)  NOT NULL UNIQUE,
    password          TEXT         NOT NULL,
    email             VARCHAR(30)  NOT NULL UNIQUE,
    avatar_url        VARCHAR(120) NOT NULL,
    created_at        DATE    DEFAULT CURRENT_TIMESTAMP,
    alliance_id       INTEGER DEFAULT NULL,
    statistic_id      INTEGER DEFAULT NULL,
    verification_code TEXT DEFAULT NULL,
    is_verified       BOOLEAN DEFAULT FALSE,
    code_death_time   BOOLEAN DEFAULT NULL,
    FOREIGN KEY (statistic_id) REFERENCES statistic (id)
);
CREATE TABLE article
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(120) UNIQUE NOT NULL,
    image_url   VARCHAR(120)        NOT NULL,
    created_at  DATE DEFAULT CURRENT_TIMESTAMP,
    description TEXT                NOT NULL,
    user_id     INTEGER             NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE TABLE alliance
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(30) UNIQUE NOT NULL,
    avatar_url    VARCHAR(120)       NOT NULL,
    tag           VARCHAR(5) UNIQUE  NOT NULL,
    leader_id     INTEGER            NOT NULL,
    members_count INT                NOT NULL,
    total_wins    INT                NOT NULL,
    FOREIGN KEY (leader_id) REFERENCES users (id)
);
CREATE TABLE game
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(30) UNIQUE NOT NULL,
    max_players     INT                NOT NULL CHECK (max_players <= 20 AND max_players > 0),
    current_players INT                NOT NULL CHECK (current_players <= 20 AND max_players > 0),
    is_active       BOOLEAN            NOT NULL DEFAULT TRUE
);
CREATE TABLE message
(
    id      SERIAL PRIMARY KEY,
    text    VARCHAR(99) NOT NULL,
    user_id INTEGER     NOT NULL,
    game_id INTEGER     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (game_id) REFERENCES game (id)
);
CREATE TABLE user_game
(
    user_id INTEGER NOT NULL,
    game_id INTEGER NOT NULL,
    primary key (user_id, game_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (game_id) REFERENCES game (id)
);
CREATE TABLE unit_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    size VARCHAR(30) NOT NULL
);
CREATE TABLE unit_template
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(45) NOT NULL,
    cost          INT         NOT NULL,
    reload        INT         NOT NULL,
    unit_type_id  INTEGER     NOT NULL,
    health_points INT         NOT NULL,
    damage        INT         NOT NULL,
    speed         INT         NOT NULL,
    range         INT         NOT NULL,
    FOREIGN KEY (unit_type_id) REFERENCES unit_type (id)
);
CREATE TABLE unit
(
    id                    SERIAL PRIMARY KEY,
    game_id               INTEGER not null,
    user_id               INTEGER not null,
    unit_template_id      INTEGER NOT NULL,
    current_health_points INT     NOT NULL CHECK (current_health_points >= 0),
    is_alive              BOOLEAN NOT NULL DEFAULT TRUE,
    x                     INTEGER NOT NULL,
    y                     INTEGER NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (unit_template_id) REFERENCES unit_template (id)
);

CREATE INDEX idx_unit_game_id ON unit (game_id);
CREATE INDEX idx_unit_user_id ON unit (user_id);

CREATE TABLE player_resources
(
    id        SERIAL PRIMARY KEY,
    user_id   INTEGER NOT NULL,
    game_id   INTEGER NOT NULL,
    resources INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (game_id) REFERENCES game (id)
);
