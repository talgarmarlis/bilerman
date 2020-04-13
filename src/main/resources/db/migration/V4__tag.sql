ALTER TABLE bilerman.image
    DROP COLUMN IF EXISTS article_id;

ALTER TABLE bilerman.article
    DROP COLUMN image_id,
    ADD COLUMN image_id INTEGER REFERENCES bilerman.image(image_id);


ALTER TABLE bilerman.draft
    DROP COLUMN article_id,
    ADD COLUMN image_id INTEGER REFERENCES bilerman.image(image_id);


CREATE TABLE IF NOT EXISTS bilerman.tag
(
    tag_id SERIAL NOT NULL
        CONSTRAINT tag_tag_id_pk
            PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(250),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS bilerman.article_tag
(
    article_tag_id SERIAL NOT NULL
        CONSTRAINT article_tag_article_tag_id_pk
            PRIMARY KEY,
    article_id INTEGER REFERENCES bilerman.article(article_id),
    tag_id INTEGER REFERENCES bilerman.tag(tag_id),
    CONSTRAINT unique_constraint_articel_tag_article_id_tag_id UNIQUE (article_id, tag_id)
);