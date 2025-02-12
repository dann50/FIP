CREATE TABLE users
(
    id                              BIGINT AUTO_INCREMENT NOT NULL,
    name                            VARCHAR(255)          NULL,
    phone_number                    VARCHAR(255)          NULL,
    email                           VARCHAR(255)          NULL,
    date_of_birth                   date                  NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);