--add influencer goods goods_status,available_stock column
ALTER TABLE love.inf_user_goods
    ADD COLUMN goods_status    int NOT NULL DEFAULT 0,
    ADD COLUMN available_stock int NOT NULL DEFAULT 0;

--update influencer goods status
update love.inf_user_goods a set goods_status = b.status from love.m_goods b where a.goods_id = b.id;

--update influencer goods available stock
update love.inf_user_goods a set available_stock = b.available_stock from love.m_goods_sku b where a.goods_id = b.id;

--update influencer goods sales volume
update love.inf_user_goods a set sales_volume  = b.sales_volume from love.m_goods b where  a.goods_id= b.id;

--update influencer goods status
update love.inf_user_goods set goods_status =4 where goods_status !=3;

--love.mer_shipping_template
DROP SEQUENCE IF EXISTS love.mer_shipping_template_id_seq;
CREATE SEQUENCE love.mer_shipping_template_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.mer_shipping_template;
CREATE TABLE love.mer_shipping_template
(
    id              BIGINT    NOT NULL DEFAULT nextval('love.mer_shipping_template_id_seq'),
    merchant_id     BIGINT    NOT NULL,
    shipping_models SMALLINT  NOT NULL,
    setting         VARCHAR(1000),
    create_time     TIMESTAMP NOT NULL,
    update_time     TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

--init data
INSERT INTO love.mer_shipping_template(merchant_id,shipping_models,create_time,update_time) SELECT id,2,now(),now() FROM love.mer_user where deleted =0;