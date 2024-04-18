--- love.r_review

DROP SEQUENCE IF EXISTS love.r_review_id_seq;
CREATE SEQUENCE love.r_review_id_seq START WITH 1000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.r_review;
CREATE TABLE love.r_review
(
    id            BIGINT    NOT NULL DEFAULT nextval('love.r_review_id_seq'),
    title         VARCHAR(100),
    content       TEXT      NOT NULL,
    rating        INT       NOT NULL DEFAULT 10,
    pid           BIGINT,
    merchant_id   BIGINT    NOT NULL,
    user_id       BIGINT    NOT NULL,
    type          SMALLINT  NOT NULL,
    related_id    BIGINT    NOT NULL,
    related_str   varchar(32),
    audit_comment VARCHAR(512),
    audit_status  SMALLINT  NOT NULL DEFAULT 0, --- 三种状态 0:pending, 1:approved,2:rejected
    audit_time    TIMESTAMP,
    create_time   TIMESTAMP NOT NULL,
    update_time   TIMESTAMP NOT NULL,
    deleted       SMALLINT  NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE INDEX index_review_merchant_id on love.r_review (merchant_id);