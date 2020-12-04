CREATE TABLE IF NOT EXISTS bilerman.language
(
    language_id SERIAL NOT NULL
        CONSTRAINT language_language_id_pk
            PRIMARY KEY,
    code VARCHAR(5) NOT NULL UNIQUE,
    label VARCHAR(50),
    created_at TIMESTAMPTZ NOT NULL
);
INSERT INTO bilerman.language (code, label, created_at)
    VALUES
        ('KG', 'Кыргызча', now()),
        ('RU', 'Русский', now()),
        ('EN', 'English', now());

ALTER TABLE bilerman.article
    ADD COLUMN language_id INTEGER REFERENCES bilerman.language(language_id)
