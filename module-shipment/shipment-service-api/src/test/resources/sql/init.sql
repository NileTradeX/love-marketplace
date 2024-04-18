--- love.shippo_merchant
DROP TABLE IF EXISTS love.shippo_merchant;
CREATE TABLE love.shippo_merchant
(
    merchant_id  BIGINT       NOT NULL PRIMARY KEY,
    access_token VARCHAR(128) NOT NULL,
    create_time  TIMESTAMP    NOT NULL DEFAULT now()
);