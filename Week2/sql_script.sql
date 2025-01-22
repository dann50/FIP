CREATE TABLE comments
(
    id           VARCHAR(255) NOT NULL,
    user_id      VARCHAR(255) NOT NULL,
    post_id      VARCHAR(255) NOT NULL,
    content      VARCHAR(255) NOT NULL,
    comment_date datetime     NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

CREATE TABLE follows
(
    id            VARCHAR(255) NOT NULL,
    follower_id   VARCHAR(255) NOT NULL,
    followee_id   VARCHAR(255) NOT NULL,
    followed_date datetime     NOT NULL,
    CONSTRAINT pk_follows PRIMARY KEY (id)
);

CREATE TABLE likes
(
    id        VARCHAR(255) NOT NULL,
    user_id   VARCHAR(255) NOT NULL,
    post_id   VARCHAR(255) NOT NULL,
    like_date datetime     NOT NULL,
    CONSTRAINT pk_likes PRIMARY KEY (id)
);

CREATE TABLE posts
(
    id        VARCHAR(255) NOT NULL,
    user_id   VARCHAR(255) NOT NULL,
    content   VARCHAR(255) NOT NULL,
    post_date datetime     NOT NULL,
    CONSTRAINT pk_posts PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE follows
    ADD CONSTRAINT uc_001950816864276edd9fe655e UNIQUE (follower_id, followee_id);

ALTER TABLE likes
    ADD CONSTRAINT uc_88d5108ea5e46ca11e828b43e UNIQUE (user_id, post_id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_POST FOREIGN KEY (post_id) REFERENCES posts (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE follows
    ADD CONSTRAINT FK_FOLLOWS_ON_FOLLOWEE FOREIGN KEY (followee_id) REFERENCES users (id);

ALTER TABLE follows
    ADD CONSTRAINT FK_FOLLOWS_ON_FOLLOWER FOREIGN KEY (follower_id) REFERENCES users (id);

ALTER TABLE likes
    ADD CONSTRAINT FK_LIKES_ON_POST FOREIGN KEY (post_id) REFERENCES posts (id);

ALTER TABLE likes
    ADD CONSTRAINT FK_LIKES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);