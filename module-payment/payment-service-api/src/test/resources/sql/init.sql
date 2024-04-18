--- love.pay_stripe_merchant

DROP TABLE IF EXISTS love.pay_stripe_merchant;
CREATE TABLE love.pay_stripe_merchant
(
    id                 SERIAL       NOT NULL PRIMARY KEY,
    merchant_id        INT          NOT NULL,
    connect_account_id VARCHAR(128) NOT NULL,
    create_time        TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE INDEX index_payment_stripe_merchant_id on love.pay_stripe_merchant (merchant_id);

--- love.pay_stripe_payment

DROP SEQUENCE IF EXISTS love.pay_stripe_payment_id_seq;
CREATE SEQUENCE love.pay_stripe_payment_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.pay_stripe_payment;
CREATE TABLE love.pay_stripe_payment
(
    id          BIGINT       NOT NULL DEFAULT nextval('love.pay_stripe_payment_id_seq'),
    user_id     BIGINT       NOT NULL,
    merchant_id BIGINT       NOT NULL,
    amount      INT          NOT NULL,
    app_fee     INT          NOT NULL,
    type        VARCHAR(16),
    order_no    VARCHAR(32)  NOT NULL,
    payment_id  VARCHAR(128) NOT NULL,
    refund_id   VARCHAR(128),
    status      smallint     not null default 0,
    pay_time    TIMESTAMP,
    create_time TIMESTAMP    NOT NULL DEFAULT now(),
    update_time TIMESTAMP    NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX index_payment_id on love.pay_stripe_payment (payment_id);
CREATE UNIQUE INDEX index_payment_order_no on love.pay_stripe_payment (order_no);
CREATE INDEX index_payment_merchant_id on love.pay_stripe_payment (merchant_id);
CREATE INDEX index_payment_user_id on love.pay_stripe_payment (user_id);


--- love.pay_adyen_merchant

DROP TABLE IF EXISTS love.pay_adyen_merchant;
CREATE TABLE love.pay_adyen_merchant
(
    id                 SERIAL       NOT NULL PRIMARY KEY,
    merchant_id        INT          NOT NULL,
    legal_entity_id    VARCHAR(128) NOT NULL,
    account_holder_id  VARCHAR(128) NOT NULL,
    balance_account_id VARCHAR(128) NOT NULL,
    create_time        TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE INDEX index_payment_adyen_merchant_id on love.pay_adyen_merchant (merchant_id);
