--- love.o_order
DROP SEQUENCE IF EXISTS love.o_order_id_seq;
CREATE SEQUENCE love.o_order_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.o_order;
CREATE TABLE love.o_order
(
    id                BIGINT        NOT NULL DEFAULT nextval('love.o_order_id_seq'),
    merchant_id       BIGINT        NOT NULL,
    order_no          VARCHAR(32)   NOT NULL,
    buyer_id          BIGINT        NOT NULL,
    buyer_name        VARCHAR(32)   NOT NULL,
    goods_id          BIGINT        NOT NULL,
    goods_title       VARCHAR(128)  NOT NULL,
    sku_id            BIGINT        NOT NULL,
    price             DECIMAL(8, 2) NOT NULL,
    qty               SMALLINT      NOT NULL DEFAULT 1,
    taxes             DECIMAL(8, 2) NOT NULL DEFAULT 0,
    shipping_fee      DECIMAL(8, 2) NOT NULL DEFAULT 0,
    app_fee           DECIMAL(8, 2) NOT NULL DEFAULT 0,
    total_amount      DECIMAL(8, 2) NOT NULL DEFAULT 0,
    consignee         VARCHAR(32)   NOT NULL,
    consignee_phone   VARCHAR(32)   NOT NULL,
    consignee_email   VARCHAR(64)   NOT NULL,
    consignee_address VARCHAR(255)  NOT NULL,
    carriers          VARCHAR(128),
    tracking_no       VARCHAR(32),
    shippo_trans_id   VARCHAR(64),
    delivery_time     TIMESTAMP,
    close_reason      VARCHAR(255),
    status            SMALLINT      NOT NULL DEFAULT 0,
    create_time       TIMESTAMP     NOT NULL,
    update_time       TIMESTAMP     NOT NULL,
    PRIMARY KEY (id)
);


CREATE UNIQUE INDEX index_order_order_no on love.o_order (order_no);
CREATE INDEX index_order_merchant_id on love.o_order (merchant_id);
CREATE INDEX index_order_buyer_id on love.o_order (buyer_id);

--- love.o_order_after_sales
DROP TABLE IF EXISTS love.o_order_after_sales;
CREATE TABLE love.o_order_after_sales
(
    id           BIGSERIAL     NOT NULL PRIMARY KEY,
    merchant_id  BIGINT        NOT NULL,
    order_id     BIGINT        NOT NULL,
    amount       DECIMAL(8, 2) NOT NULL,
    type         SMALLINT      NOT NULL,
    refund_type  SMALLINT      NOT NULL,
    order_status SMALLINT      NOT NULL,
    status       SMALLINT      NOT NULL DEFAULT 0,
    reason       VARCHAR(512)  NOT NULL,
    comment      VARCHAR(512),
    create_time  TIMESTAMP     NOT NULL,
    update_time  TIMESTAMP     NOT NULL
);

CREATE INDEX index_after_sales_merchant_id on love.o_order_after_sales (merchant_id);
CREATE INDEX index_after_sales_order_id on love.o_order_after_sales (order_id);
