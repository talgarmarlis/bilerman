ALTER TABLE bilerman.article_tag
    DROP CONSTRAINT article_tag_article_id_fkey;
ALTER TABLE bilerman.article_tag
    ADD CONSTRAINT article_tag_article_id_fkey FOREIGN KEY (article_id) REFERENCES article (article_id) ON DELETE CASCADE;

ALTER TABLE bilerman.saved_article
    DROP CONSTRAINT saved_article_article_id_fkey;
ALTER TABLE bilerman.saved_article
    ADD CONSTRAINT saved_article_article_id_fkey FOREIGN KEY (article_id) REFERENCES article (article_id) ON DELETE CASCADE;

ALTER TABLE bilerman.clap
    DROP CONSTRAINT clap_article_id_fkey;
ALTER TABLE bilerman.clap
    ADD CONSTRAINT clap_article_id_fkey FOREIGN KEY (article_id) REFERENCES article (article_id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS bilerman.comment
(
    comment_id SERIAL NOT NULL
        CONSTRAINT comment_comment_id_pk
            PRIMARY KEY,
    article_id INTEGER NOT NULL REFERENCES bilerman.article(article_id) ON DELETE CASCADE,
    message VARCHAR NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by INTEGER NOT NULL REFERENCES bilerman.user(user_id)
);

CREATE TABLE IF NOT EXISTS bilerman.reply
(
    reply_id SERIAL NOT NULL
        CONSTRAINT reply_reply_id_pk
            PRIMARY KEY,
    comment_id INTEGER NOT NULL REFERENCES bilerman.comment(comment_id) ON DELETE CASCADE,
    message VARCHAR NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by INTEGER NOT NULL REFERENCES bilerman.user(user_id)
);