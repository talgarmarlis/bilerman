ALTER TABLE bilerman.article
    ADD COLUMN is_public BOOLEAN NOT NULL DEFAULT TRUE,
    ADD COLUMN is_listed BOOLEAN NOT NULL DEFAULT TRUE;
