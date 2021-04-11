ALTER TABLE bilerman.user
    DROP COLUMN avatar,
    DROP COLUMN mind,
    DROP COLUMN cover;
ALTER TABLE bilerman.user
    ADD COLUMN avatar INTEGER REFERENCES bilerman.image(image_id),
    ADD COLUMN facebook_id VARCHAR UNIQUE
