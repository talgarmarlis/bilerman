CREATE TABLE IF NOT EXISTS bilerman.draft
(
    draft_id SERIAL NOT NULL
        CONSTRAINT draft_draft_id_pk
			PRIMARY KEY,
	title VARCHAR(500),
	preview VARCHAR(500),
	body TEXT,
	published BOOLEAN NOT NULL DEFAULT FALSE,
	article_id INTEGER REFERENCES bilerman.article(article_id),
	created_at TIMESTAMPTZ NOT NULL,
	updated_at TIMESTAMPTZ NOT NULL,
	created_by INTEGER NOT NULL REFERENCES bilerman.user(user_id),
	updated_by INTEGER NOT NULL REFERENCES bilerman.user(user_id)
);

ALTER TABLE bilerman.article
DROP COLUMN description,
DROP COLUMN published,
DROP COLUMN updated_at,
DROP COLUMN updated_by,
ADD COLUMN draft_id INTEGER NOT NULL REFERENCES bilerman.draft(draft_id);


