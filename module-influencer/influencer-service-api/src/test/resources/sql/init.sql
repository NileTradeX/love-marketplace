--love.inf_user
DROP SEQUENCE IF EXISTS love.inf_user_id_seq;
CREATE SEQUENCE love.inf_user_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.inf_user;
CREATE TABLE love.inf_user
(
    id                   BIGINT      NOT NULL DEFAULT nextval('love.inf_user_id_seq'),
    account              VARCHAR(32) NOT NULL,
    username             VARCHAR(32) NOT NULL,
    "password"           VARCHAR(64) NOT NULL,
    avatar               VARCHAR(255) NULL,
    general_introduction VARCHAR(1000),
    social_links         VARCHAR(2000),
    first_name           VARCHAR(20) NULL,
    last_name            VARCHAR(20) NULL,
    code                 VARCHAR(50) NOT NULL,
    last_login_time      TIMESTAMP   NOT NULL DEFAULT now(),
    create_time          TIMESTAMP   NOT NULL,
    update_time          TIMESTAMP   NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX index_inf_user_account ON love.inf_user USING btree (account);

--love.inf_store
DROP SEQUENCE IF EXISTS love.inf_store_id_seq;
CREATE SEQUENCE love.inf_store_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.inf_store;
CREATE TABLE love.inf_store
(
    id              BIGINT    NOT NULL DEFAULT nextval('love.inf_store_id_seq'),
    cover           VARCHAR(255) NULL,
    title           VARCHAR(50) NULL,
    display_name    VARCHAR(50),
    goods_sort_type INT NULL,
    description     VARCHAR(1000) NULL,
    influencer_id   INT       NOT NULL,
    create_time     TIMESTAMP NOT NULL,
    update_time     TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

--love.inf_user_address
DROP SEQUENCE IF EXISTS love.inf_user_address_id_seq;
CREATE SEQUENCE love.inf_user_address_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.inf_user_address;
CREATE TABLE love.inf_user_address
(
    id            BIGINT       NOT NULL DEFAULT nextval('love.inf_user_address_id_seq'),
    influencer_id int          NOT NULL,
    country       VARCHAR(50)  NOT NULL,
    phone_number  VARCHAR(16)  NOT NULL,
    city          VARCHAR(50)  NOT NULL,
    state         SMALLINT     NOT NULL,
    zip_code      VARCHAR(8)   NOT NULL,
    address       VARCHAR(100) NOT NULL,
    is_default    int          NOT NULL DEFAULT 0,
    create_time   TIMESTAMP    NOT NULL,
    update_time   TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX index_inf_user_address_influencer_id ON love.inf_user_address USING btree (influencer_id);

--love.inf_user_goods
DROP SEQUENCE IF EXISTS love.inf_user_goods_id_seq;
CREATE SEQUENCE love.inf_user_goods_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.inf_user_goods;
CREATE TABLE love.inf_user_goods
(
    id              BIGINT        NOT NULL DEFAULT nextval('love.inf_user_goods_id_seq'),
    goods_id        int           NOT NULL,
    influencer_id   int           NOT NULL,
    status          int           NOT NULL DEFAULT 0,
    sort_num        int NULL,
    sales_volume    int           NOT NULL DEFAULT 0,
    min_price       DECIMAL(8, 2) NOT NULL,
    max_price       DECIMAL(8, 2) NOT NULL,
    commission_rate DECIMAL(8, 2) NOT NULL DEFAULT 10,
    community_score SMALLINT NULL,
    create_time     TIMESTAMP     NOT NULL,
    update_time     TIMESTAMP     NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX index_inf_user_goods_id ON love.inf_user_goods USING btree (goods_id);
CREATE INDEX index_inf_user_influencer_id ON love.inf_user_goods USING btree (influencer_id);

--love.inf_user_order
DROP SEQUENCE IF EXISTS love.inf_user_order_id_seq;
CREATE SEQUENCE love.inf_user_order_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.inf_user_order;
CREATE TABLE love.inf_user_order
(
    id            BIGINT        NOT NULL DEFAULT nextval('love.inf_user_order_id_seq'),
    influencer_id int           NOT NULL,
    buyer_id      int           NOT NULL,
    order_id      int           NOT NULL,
    order_no      VARCHAR(32)   NOT NULL,
    total_amount  DECIMAL(8, 2) NOT NULL DEFAULT 0,
    commission    DECIMAL(8, 2) NOT NULL DEFAULT 0,
    create_time   TIMESTAMP     NOT NULL,
    update_time   TIMESTAMP     NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX index_inf_user_order_id ON love.inf_user_order USING btree (order_id);
CREATE INDEX index_inf_user_order_influencer_id ON love.inf_user_order USING btree (influencer_id);

--love.inf_user_hits
DROP SEQUENCE IF EXISTS love.inf_user_hits_id_seq;
CREATE SEQUENCE love.inf_user_hits_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.inf_user_hits;
CREATE TABLE love.inf_user_hits
(
    id            BIGINT      NOT NULL DEFAULT nextval('love.inf_user_hits_id_seq'),
    influencer_id int         NOT NULL,
    create_time   TIMESTAMP   NOT NULL,
    ip            VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX index_inf_user_hits_influencer_id ON love.inf_user_hits USING btree (influencer_id);

-- init data
INSERT INTO love.inf_user (account, username, "password", avatar, general_introduction, social_links, first_name,
                           last_name, last_login_time, create_time, update_time, code)
VALUES ('jason@love.com', 'jason', '$2a$10$M5bHe9KHzQLfCUwgFj1f5eoPV7tgdhPeF4GRZP5CGz1Qfn6vLe5ay', '', '', '', 'jason',
        'zhou', '2023-05-30 13:38:05.510', '2023-05-24 12:00:00.000', '2023-05-26 13:31:07.937', '6439816630267498993');

