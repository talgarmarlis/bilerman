CREATE TABLE IF NOT EXISTS bilerman.article
(
    article_id SERIAL NOT NULL
        CONSTRAINT article_article_id_pk
			PRIMARY KEY,
	title VARCHAR(500) NOT NULL,
	subtitle VARCHAR(500),
	preview VARCHAR(500),
	body TEXT,
	published BOOLEAN NOT NULL DEFAULT FALSE,
	views INTEGER NOT NULL DEFAULT 0,
	image_id INTEGER,
	created_at TIMESTAMPTZ NOT NULL,
	updated_at TIMESTAMPTZ NOT NULL,
	created_by INTEGER NOT NULL REFERENCES bilerman.user(user_id),
	updated_by INTEGER NOT NULL REFERENCES bilerman.user(user_id)
);

CREATE TABLE IF NOT EXISTS bilerman.image
(
	image_id SERIAL NOT NULL
		CONSTRAINT image_image_id_pk
			PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	article_id INTEGER NOT NULL REFERENCES bilerman.article(article_id) ON DELETE CASCADE,
	created_at TIMESTAMPTZ NOT NULL
);

