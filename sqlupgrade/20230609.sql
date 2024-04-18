--- love.u_shopping_cart_goods

DROP SEQUENCE IF EXISTS love.u_shopping_cart_goods_id_seq;
CREATE SEQUENCE love.u_shopping_cart_goods_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.u_shopping_cart_goods;
CREATE TABLE love.u_shopping_cart_goods
(
    id                   BIGINT        NOT NULL DEFAULT nextval('love.u_shopping_cart_goods_id_seq'),
    user_id              BIGINT        NOT NULL,
    goods_id             BIGINT        NOT NULL,
    sku_id               BIGINT        NOT NULL,
    price                DECIMAL(8, 2) NOT NULL,
    qty                  INT           NOT NULL,
    shipping_template_id BIGINT,
    create_time          TIMESTAMP     NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX index_u_cart_user_id on love.u_shopping_cart_goods (user_id);
CREATE UNIQUE INDEX unique_index_u_cart_user_id_goods_id_sku_id on love.u_shopping_cart_goods (user_id, goods_id, sku_id);