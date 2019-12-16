CREATE TABLE IF NOT EXISTS bilerman.user
(
    user_id SERIAL NOT NULL
        CONSTRAINT user_user_id_pk
			PRIMARY KEY,
	email VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(250) NOT NULL,
	name VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	avatar VARCHAR(100),
	created_at TIMESTAMPTZ NOT NULL,
	updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS bilerman.role
(
	role_id SERIAL NOT NULL
		CONSTRAINT role_role_id_pk
			PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS bilerman.user_role
(
    user_role_id SERIAL NOT NULL
        CONSTRAINT user_role_user_role_id_pk
            PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES bilerman.user(user_id) ON DELETE CASCADE,
    role_id INTEGER NOT NULL REFERENCES bilerman.role(role_id) ON DELETE CASCADE
);

INSERT INTO bilerman.role(role_id, name) VALUES(DEFAULT, 'ROLE_USER'), (DEFAULT, 'ROLE_ADMIN');

