ALTER TABLE "love"."m_goods" ADD COLUMN "organic" int2;
ALTER TABLE "love"."m_goods" ADD COLUMN "sustainability" varchar(16);
alter table love.mer_user_admin_business_info add column biz_size int2 not null default 1;

alter table love.inf_user alter column username drop not null;
alter table love.inf_user alter column last_login_time drop not null;
alter table love.inf_user add commission_rate decimal(8, 2) default 10 not null;
alter table love.inf_user add status int2 default 0 not null;
alter table love.inf_user add paypal_account varchar(32) ;


alter table love.inf_user_order add column mer_commission_rate numeric(4,2) NOT NULL DEFAULT 0.0;
alter table love.inf_user_order add column commission_rate numeric(4,2) NOT NULL DEFAULT 0.0;
alter table love.inf_user_order add column refund_amount numeric(8,2) NOT NULL DEFAULT 0.0;
alter table love.inf_user_order add goods_id int8 NOT NULL;
alter table love.inf_user_order add sku_id int8 NOT NULL;
alter table love.inf_user_order rename column  order_no to order_item_no;

CREATE SEQUENCE "love"."inf_user_withdraw_record_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

create table love.inf_user_withdraw_record(
	"id" int8 NOT NULL DEFAULT nextval('"love".inf_user_withdraw_record_id_seq'::regclass),
	"influencer_id" int4 NOT NULL,
	"amount" numeric(8,2) DEFAULT 0.0,
	"payment_id" varchar(128),
	"pay_time" timestamp(6),
	"update_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	"create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT "inf_user_cash_record_pkey" PRIMARY KEY ("id")
);

alter table love.u_shopping_cart_goods add update_time timestamp default now();
alter table love.u_shopping_cart_goods add influencer_code varchar(16);