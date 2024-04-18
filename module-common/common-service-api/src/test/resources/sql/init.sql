-- love.r_resource
DROP TABLE IF EXISTS love.r_resource;
CREATE TABLE love.r_resource
(
    id          bigserial    NOT NULL PRIMARY KEY,
    user_id     BIGINT       NOT NULL,
    ori_name    VARCHAR(128) NOT NULL,
    size        INT          NOT NULL DEFAULT 1,
    key         VARCHAR(128) NOT NULL,
    ext         VARCHAR(8)   NOT NULL,
    type        SMALLINT     NOT NULL DEFAULT 1,
    width       INT          NOT NULL DEFAULT 0,
    height      INT          NOT NULL DEFAULT 0,
    create_time TIMESTAMP    NOT NULL,
    update_time TIMESTAMP    NOT NULL
);

-- love.d_key_value
DROP TABLE IF EXISTS love.d_key_value;
CREATE TABLE love.d_key_value
(
    id          serial        NOT NULL PRIMARY KEY,
    key         VARCHAR(128)  NOT NULL,
    value       VARCHAR(1024) NOT NULL,
    remark      VARCHAR(512)  NOT NULL DEFAULT '',
    timeless    smallint      NOT NULL DEFAULT 1,
    begin_time  TIMESTAMP,
    end_time    TIMESTAMP,
    create_time TIMESTAMP     NOT NULL,
    update_time TIMESTAMP     NOT NULL
);

CREATE UNIQUE INDEX index_key_value_key on love.d_key_value (key);