CREATE TABLE IF NOT EXISTS bilerman.question
(
    question_id SERIAL NOT NULL CONSTRAINT question_question_id_pk PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    description TEXT,
    response_id INTEGER,
    is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by INTEGER NOT NULL REFERENCES bilerman.user(user_id),
    updated_by INTEGER NOT NULL REFERENCES bilerman.user(user_id)
);

CREATE TABLE IF NOT EXISTS bilerman.question_tag
(
    question_id INTEGER NOT NULL REFERENCES bilerman.question (question_id) ON DELETE CASCADE,
    tag_id     INTEGER NOT NULL REFERENCES bilerman.tag (tag_id) ON DELETE CASCADE,
    CONSTRAINT unique_constraint_question_tag_question_id_tag_id UNIQUE (question_id, tag_id)
);

CREATE TABLE IF NOT EXISTS bilerman.response
(
    response_id SERIAL NOT NULL CONSTRAINT response_response_id_pk PRIMARY KEY,
    message TEXT NOT NULL,
    question_id INTEGER NOT NULL REFERENCES bilerman.question(question_id) ON DELETE CASCADE,
    is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by INTEGER NOT NULL REFERENCES bilerman.user(user_id)
);

CREATE TABLE IF NOT EXISTS bilerman.response_reply
(
    response_reply_id SERIAL NOT NULL CONSTRAINT response_reply_response_reply_id_pk PRIMARY KEY,
    response_id INTEGER NOT NULL REFERENCES bilerman.response(response_id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by INTEGER NOT NULL REFERENCES bilerman.user(user_id)
);

CREATE TABLE IF NOT EXISTS bilerman.response_vote
(
    elected BOOLEAN NOT NULL,
    response_id INTEGER NOT NULL REFERENCES bilerman.response(response_id) ON DELETE CASCADE,
    user_id INTEGER NOT NULL REFERENCES bilerman.user(user_id),
    CONSTRAINT unique_constraint_response_vote_response_id_user_id UNIQUE(response_id, user_id)
);

CREATE TABLE IF NOT EXISTS bilerman.question_like
(
    question_id INTEGER NOT NULL REFERENCES bilerman.question(question_id) ON DELETE CASCADE,
    user_id INTEGER NOT NULL REFERENCES bilerman.user(user_id),
    CONSTRAINT unique_constraint_question_like_question_id_user_id UNIQUE(question_id, user_id)
);