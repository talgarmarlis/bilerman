ALTER TABLE bilerman.article_tag DROP COLUMN article_tag_id;

CREATE TABLE IF NOT EXISTS bilerman.saved_article
(
    article_id INTEGER REFERENCES bilerman.article(article_id),
    user_id INTEGER REFERENCES bilerman.user(user_id),
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT unique_constraint_saved_article_article_id_user_id UNIQUE (article_id, user_id)
);

CREATE TABLE IF NOT EXISTS bilerman.clap
(
    article_id INTEGER REFERENCES bilerman.article(article_id),
    user_id INTEGER REFERENCES bilerman.user(user_id),
    count SMALLINT NOT NULL CHECK ( count <= 100 ),
    CONSTRAINT unique_constraint_clap_article_id_user_id UNIQUE (article_id, user_id)
);