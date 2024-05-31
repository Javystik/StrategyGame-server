INSERT INTO statistic(player_games, win_games, enemy_units_killed, units_deaths,
                      territories_captured, territories_lost)
VALUES (1000, 1000, 1000, 1000, 1000, 1000),
       (0, 0, 0, 0, 0, 0);

INSERT INTO users(username, password, email, avatar_url, created_at, statistic_id, is_verified)
VALUES ('admin', '$2a$10$2YgPoocJEVyRa4siDTuRXO0lhaka3uLfS8FVqro2Sic9ge1bVUxxC', 'admin@gmail.com',
        'base/user-avatar.png', CURRENT_TIMESTAMP, 1, true),
       ('user', '$2a$10$UdrDanvxY.YZ.MorQIsKQO2igKJpOeqVaOTW/ATLD4LwkzsEFK6mG', 'user@gmail.com',
        'base/user-avatar.png', CURRENT_TIMESTAMP, 2, false);

INSERT INTO user_role(user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);
