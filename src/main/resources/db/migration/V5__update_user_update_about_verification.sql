ALTER TABLE users
    ADD COLUMN verification_code VARCHAR(40);

ALTER TABLE users
    ADD COLUMN is_verified BOOLEAN DEFAULT FALSE;
ALTER TABLE users
    ADD COLUMN code_death_time DEFAULT (datetime('now', '+1 minute'));


