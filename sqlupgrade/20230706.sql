ALTER TABLE "love"."o_order"
    ADD COLUMN "buyer_type" int2 NOT NULL DEFAULT 0,
    ALTER COLUMN "buyer_name" DROP NOT NULL;

CREATE SEQUENCE love.u_guest_id_seq;

DROP TABLE IF EXISTS "love"."u_guest";
CREATE TABLE "love"."u_guest" (
    "id" int8 NOT NULL DEFAULT nextval('"love".u_guest_id_seq'::regclass),
    "email" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "create_time" timestamp(6) NOT NULL,
    "update_time" timestamp(6) NOT NULL,
    CONSTRAINT "u_guest_pkey" PRIMARY KEY ("id")
)
;

ALTER TABLE "love"."pay_stripe_payment"
    ADD COLUMN "channel" int2 NOT NULL DEFAULT 0,
    ALTER COLUMN "user_id" DROP NOT NULL;

ALTER TABLE love.pay_stripe_payment RENAME TO pay_info;
