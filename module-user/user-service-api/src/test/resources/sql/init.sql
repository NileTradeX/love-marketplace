--- love.u_user

DROP SEQUENCE IF EXISTS love.u_user_id_seq;
CREATE SEQUENCE love.u_user_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.u_user;
CREATE TABLE love.u_user
(
    id                      BIGINT      NOT NULL DEFAULT nextval('love.u_user_id_seq'),
    email                   VARCHAR(32) NOT NULL,
    password                VARCHAR(64) NOT NULL,
    first_name              VARCHAR(64) NOT NULL,
    last_name               VARCHAR(64) NOT NULL,
    avatar                  VARCHAR(255),
    status                  SMALLINT    NOT NULL DEFAULT 1,
    source                  SMALLINT    NOT NULL DEFAULT 0,
    last_login_time         TIMESTAMP   NOT NULL DEFAULT now(),
    notes                   VARCHAR(2048),
    uid                     VARCHAR(32) NOT NULL,
    accept_terms_of_service SMALLINT    NOT NULL DEFAULT 0,
    subscribe_to_newsletter SMALLINT    NOT NULL DEFAULT 0,
    create_time             TIMESTAMP   NOT NULL,
    update_time             TIMESTAMP   NOT NULL,
    deleted                 SMALLINT    NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX index_user_email_deleted on love.u_user (email, deleted);

--- love.u_address

DROP SEQUENCE IF EXISTS love.u_address_id_seq;
CREATE SEQUENCE love.u_address_id_seq START WITH 1000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.u_address;
CREATE TABLE love.u_address
(
    id           BIGINT       NOT NULL DEFAULT nextval('love.u_address_id_seq'),
    user_id      BIGINT       NOT NULL,
    first_name   VARCHAR(20)  NOT NULL,
    last_name    VARCHAR(20)  NOT NULL,
    phone_number VARCHAR(16)  NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    state        SMALLINT     NOT NULL,
    zip_code     VARCHAR(8)   NOT NULL,
    company      VARCHAR(50),
    address      VARCHAR(100) NOT NULL,
    is_default   INT          NOT NULL DEFAULT 0,
    create_time  TIMESTAMP    NOT NULL,
    update_time  TIMESTAMP    NOT NULL,
    deleted      SMALLINT     NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE INDEX index_user_address_user_id on love.u_address (user_id);
