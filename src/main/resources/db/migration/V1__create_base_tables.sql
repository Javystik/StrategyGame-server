CREATE TABLE users
(
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    username          VARCHAR(30)  NOT NULL UNIQUE,
    password          TEXT         NOT NULL,
    email             VARCHAR(30)  NOT NULL UNIQUE,
    avatar_url        VARCHAR(120) NOT NULL,
    created_at        DATE    DEFAULT CURRENT_TIMESTAMP,
    alliance_id       INTEGER DEFAULT NULL,
    statistic_id      INTEGER DEFAULT NULL,
    verification_code INTEGER DEFAULT NULL,
    is_verified       BOOLEAN DEFAULT FALSE,
    code_death_time   BOOLEAN DEFAULT NULL,
    FOREIGN KEY (statistic_id) REFERENCES statistic (id)
);

CREATE TABLE statistic
(
    id                   INTEGER PRIMARY KEY AUTOINCREMENT,
    player_games         INT DEFAULT 0,
    win_games            INT DEFAULT 0,
    enemy_units_killed   INT DEFAULT 0,
    units_deaths         INT DEFAULT 0,
    territories_captured INT DEFAULT 0,
    territories_lost     INT DEFAULT 0
);
CREATE TABLE article
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        VARCHAR(120) UNIQUE NOT NULL,
    image_url   VARCHAR(120)        NOT NULL,
    created_at  DATE DEFAULT CURRENT_TIMESTAMP,
    description TEXT                NOT NULL,
    user_id     INTEGER             NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE alliance
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    name          VARCHAR(30) UNIQUE NOT NULL,
    avatar_url    VARCHAR(120)       NOT NULL,
    tag           VARCHAR(5) UNIQUE  NOT NULL,
    leader_id     INTEGER            NOT NULL,
    members_count INT                NOT NULL,
    total_wins    INT                NOT NULL,
    FOREIGN KEY (leader_id) REFERENCES users (id)
);
CREATE TABLE unit_type
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(30) NOT NULL
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