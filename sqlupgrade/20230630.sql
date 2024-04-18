--- love.o_merchant_order
DROP SEQUENCE IF EXISTS love.o_merchant_order_id_seq;
CREATE SEQUENCE love.o_merchant_order_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.o_merchant_order;
CREATE TABLE love.o_merchant_order
(
    id           BIGINT        NOT NULL DEFAULT nextval('love.o_merchant_order_id_seq'),
    brand_id     BIGINT        NOT NULL,
    order_id     BIGINT        NOT NULL,
    buyer_id     BIGINT        NOT NULL,
    buyer_type   SMALLINT      NOT NULL,
    merchant_id  BIGINT        NOT NULL,
    order_no     VARCHAR(32)   NOT NULL,
    reason       VARCHAR(255),
    mer_order_no VARCHAR(32)   NOT NULL,
    total_amount DECIMAL(8, 2) NOT NULL,
    status       SMALLINT      NOT NULL DEFAULT 0,
    create_time  TIMESTAMP     NOT NULL,
    update_time  TIMESTAMP     NOT NULL,
    PRIMARY KEY (id)
);

--- love.o_order_item
DROP SEQUENCE IF EXISTS love.o_order_item_id_seq;
CREATE SEQUENCE love.o_order_item_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.o_order_item;
CREATE TABLE love.o_order_item
(
    id                BIGINT        NOT NULL DEFAULT nextval('love.o_order_item_id_seq'),
    order_id          BIGINT        NOT NULL,
    merchant_id       BIGINT        NOT NULL,
    merchant_order_id BIGINT        NOT NULL,
    order_item_no     VARCHAR(32)   NOT NULL,
    goods_id          BIGINT        NOT NULL,
    goods_title       VARCHAR(128)  NOT NULL,
    sku_id            BIGINT        NOT NULL,
    price             DECIMAL(8, 2) NOT NULL,
    qty               SMALLINT      NOT NULL DEFAULT 1,
    status            SMALLINT      NOT NULL DEFAULT 0,
    carriers          varchar(128),
    tracking_no       varchar(32),
    delivery_time     timestamp,
    create_time       TIMESTAMP     NOT NULL,
    update_time       TIMESTAMP     NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX index_order_item_order_id on love.o_order_item (order_id);

ALTER TABLE love.o_order alter column merchant_id drop not null;
ALTER TABLE love.o_order alter column goods_id drop not null;
ALTER TABLE love.o_order alter column goods_title drop not null;
ALTER TABLE love.o_order alter column sku_id drop not null;
ALTER TABLE love.o_order alter column price drop not null;
ALTER TABLE love.o_order alter column qty drop not null;




CREATE TABLE "love"."order_refund" (
  "refund_no" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "after_sale_no" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "third_refund_no" varchar(256) COLLATE "pg_catalog"."default",
  "buyer_id" int8 NOT NULL,
  "order_no" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "mer_order_no" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "merchant_id" int8 NOT NULL DEFAULT 0,
  "refund_amount" numeric(10,2) NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "refund_time" timestamp(6),
  CONSTRAINT "order_refund_pkey" PRIMARY KEY ("refund_no")
)
;

ALTER TABLE "love"."order_refund" 
  OWNER TO "mpdev";

CREATE INDEX "index_after_sales_merchant_id_copy1_copy3" ON "love"."order_refund" USING btree (
  "refund_no" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "index_after_sales_order_id_copy1_copy3" ON "love"."order_refund" USING btree (
  "after_sale_no" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE SEQUENCE order_after_sales_sku_id_seq;

CREATE TABLE "love"."order_after_sales_sku" (
  "id" int8 NOT NULL DEFAULT nextval('"love".order_after_sales_sku_id_seq'::regclass),
  "merchant_id" int8 NOT NULL,
  "order_no" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "buyer_id" int8 NOT NULL,
  "after_sale_no" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "goods_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "sku_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "price" numeric(8,2) NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "mer_order_no" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "goods_title" varchar(1024) COLLATE "pg_catalog"."default",
  "refund_amount" numeric,
  "qty" int4,
  "sku_img" varchar(255) COLLATE "pg_catalog"."default",
  "sku_info" varchar(1024) COLLATE "pg_catalog"."default",
  CONSTRAINT "o_order_copy1_pkey" PRIMARY KEY ("id")
)
;

ALTER TABLE "love"."order_after_sales_sku" 
  OWNER TO "mpdev";
	
	
	
CREATE TABLE "love"."order_after_sales_record" (
  "after_sale_no" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "buyer_id" int4 NOT NULL,
  "sale_amount" numeric(16,2) NOT NULL,
  "pay_amount" numeric(16,2) NOT NULL,
  "after_sale_type" int2 NOT NULL,
  "after_sale_reason" varchar(512) COLLATE "pg_catalog"."default",
  "shipping_fee" numeric(16,2) NOT NULL,
  "after_sale_state" int2 NOT NULL,
  "refund_no" varchar(64) COLLATE "pg_catalog"."default",
  "refund_amount" numeric(16,2),
  "refund_status" int2 NOT NULL,
  "after_sale_status" int2 NOT NULL,
  "after_sale_remark" varchar(512) COLLATE "pg_catalog"."default",
  "merchant_id" int8 NOT NULL,
  "order_no" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "mer_order_no" varchar(128) COLLATE "pg_catalog"."default",
  "consumer_deal_status" int2,
  "consumer_deal_result" int2,
  "consumer_deal_time" timestamp(6),
  "merchant_deal_status" int2,
  "merchant_deal_result" int2,
  "merchant_deal_time" timestamp(6),
  "brand_id" int8 NOT NULL,
  "merchant_deal_desc" varchar(512) COLLATE "pg_catalog"."default",
  CONSTRAINT "order_after_sales_record_pkey" PRIMARY KEY ("after_sale_no")
)
;

ALTER TABLE "love"."order_after_sales_record" 
  OWNER TO "mpdev";

COMMENT ON COLUMN "love"."order_after_sales_record"."sale_amount" IS 'Not include express fee';

COMMENT ON COLUMN "love"."order_after_sales_record"."pay_amount" IS 'Include express fee';

COMMENT ON COLUMN "love"."order_after_sales_record"."after_sale_type" IS '1redund money';

COMMENT ON COLUMN "love"."order_after_sales_record"."after_sale_state" IS '1.apply: to merchant deal 2.merchant agree 3.complete refund 4.merchant reject 5.cancle';

COMMENT ON COLUMN "love"."order_after_sales_record"."refund_status" IS '0not refund 1refunded 2close';

COMMENT ON COLUMN "love"."order_after_sales_record"."after_sale_status" IS '1.Refund requested 2.Refunded 3.Cancelled 4.Rejected';

COMMENT ON COLUMN "love"."order_after_sales_record"."consumer_deal_status" IS '0.prepare deal 1.dealed';

COMMENT ON COLUMN "love"."order_after_sales_record"."consumer_deal_result" IS '0.not deal 1.cancle';

COMMENT ON COLUMN "love"."order_after_sales_record"."merchant_deal_status" IS '0.prepare deal 1.dealed';

COMMENT ON COLUMN "love"."order_after_sales_record"."merchant_deal_result" IS '0.prepare deal 1.agree 2.reject';



INSERT INTO "order_after_sales_record" 
("after_sale_no", "buyer_id", "sale_amount", "pay_amount", "after_sale_type", 
"after_sale_reason", "shipping_fee", "after_sale_state", "refund_no", "refund_amount", 
"refund_status", "after_sale_status", "after_sale_remark", "merchant_id", 
"order_no", "create_time", "update_time", "mer_order_no", "consumer_deal_status", 
"consumer_deal_result", "consumer_deal_time", "merchant_deal_status", "merchant_deal_result", 
"merchant_deal_time", "brand_id")
select "after_sale_no", "buyer_id", "sale_amount", "pay_amount", "after_sale_type", 
"after_sale_reason", "shipping_fee", "after_sale_state", "refund_no", "refund_amount", 
"refund_status", "after_sale_status", "after_sale_remark", "merchant_id", 
"order_no", "create_time", "update_time", "mer_order_no", "consumer_deal_status", 
"consumer_deal_result", "consumer_deal_time", "merchant_deal_status", "merchant_deal_result", 
"merchant_deal_time", "brand_id" from (
select t1."id" as "after_sale_no" ,t4.buyer_id as buyer_id,t1.amount as sale_amount, 
t1.amount as pay_amount, 1 as after_sale_type, 
t1.reason as after_sale_reason, 0.00 as shipping_fee, CASE 
 WHEN t1.status = 1 THEN 1 -- pending
 WHEN t1.status = 5 THEN 100 -- reject
 WHEN t1.status = 10 and t4.status = 50 THEN 99 -- refund success
 WHEN t1.status = 10 and t4.status != 50 THEN 100 -- cancel
 ELSE 1 END as after_sale_state , 
 null as refund_no, t1."amount" as refund_amount, 
CASE 
 WHEN t1.status = 1 THEN 0 -- default refund status 
 WHEN t1.status = 5 THEN 0 -- default refund status
 WHEN t1.status = 10 and t4.status = 50 THEN 1 -- refund success
 WHEN t1.status = 10 and t4.status != 50 THEN 0 -- default refund status
 ELSE 0 END as "refund_status", 
CASE 
 WHEN t1.status = 1 THEN 1 -- request refund
 WHEN t1.status = 5 THEN 5 -- reject
 WHEN t1.status = 10 and t4.status = 50 THEN 3 -- refund success
 WHEN t1.status = 10 and t4.status != 50 THEN 5 -- reject
 ELSE 1 END as "after_sale_status", t1."comment" as after_sale_remark, t1."merchant_id", 
t4."order_no", t1."create_time", t1."update_time", t3."mer_order_no", 
0 as "consumer_deal_status", 
0 as "consumer_deal_result", t1.update_time as "consumer_deal_time", 
CASE 
 WHEN t1.status = 1 THEN 0 -- request refund
 WHEN t1.status = 5 THEN 1 -- reject
 WHEN t1.status = 10 and t4.status = 50 THEN 1 -- refund success
 WHEN t1.status = 10 and t4.status != 50 THEN 1 -- reject
 ELSE 1 END as "merchant_deal_status", 
CASE 
 WHEN t1.status = 1 THEN 0 -- request refund
 WHEN t1.status = 5 THEN 2 -- reject
 WHEN t1.status = 10 and t4.status = 50 THEN 1 -- refund success
 WHEN t1.status = 10 and t4.status != 50 THEN 2 -- reject
 ELSE 1 END as "merchant_deal_result", 
t1.update_time as "merchant_deal_time", t3."brand_id" 
  from o_order_after_sales t1 left join o_order_item t2 on t1.order_id = t2.order_id 
left join o_merchant_order t3 on t1.order_id = t3.order_id left join o_order t4 on t1.order_id = t4.id 
) a1 where a1.order_no is not null;


INSERT INTO "order_after_sales_sku" 
("id", "merchant_id", "order_no", "buyer_id", "after_sale_no", "goods_id", "sku_id", 
"price", "create_time", "update_time", "mer_order_no", "goods_title", "refund_amount", 
"qty", "sku_img", "sku_info") 
select "id", "merchant_id", "order_no", "buyer_id", "after_sale_no", "goods_id", "sku_id", 
"price", "create_time", "update_time", "mer_order_no", "goods_title", "refund_amount", 
"qty", "sku_img", "sku_info"
from (
select t1."id" , t1."merchant_id", t4."order_no", t4."buyer_id", t1."id" as "after_sale_no", 
t2."goods_id", t2."sku_id", 
t2."price", t1."create_time", t1."update_time", t3."mer_order_no", t2."goods_title", t1.amount as "refund_amount", 
t2."qty", null as "sku_img", null as "sku_info"
  from o_order_after_sales t1 left join o_order_item t2 on t1.order_id = t2.order_id 
left join o_merchant_order t3 on t1.order_id = t3.order_id left join o_order t4 on t1.order_id = t4.id 
) a1 where a1.order_no is not null;






