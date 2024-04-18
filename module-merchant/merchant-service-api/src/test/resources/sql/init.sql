--- love.mer_group
DROP TABLE IF EXISTS love.mer_group;
CREATE TABLE love.mer_group
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    name        VARCHAR(32) NOT NULL,
    create_time TIMESTAMP   NOT NULL
);

CREATE UNIQUE INDEX index_mer_group_name on love.mer_group (name);

--- love.mer_user
DROP TABLE IF EXISTS love.mer_user;
CREATE TABLE love.mer_user
(
    id              SERIAL      NOT NULL PRIMARY KEY,
    account         VARCHAR(32) NOT NULL,
    username        VARCHAR(32) NOT NULL,
    password        VARCHAR(64) NOT NULL,
    status          SMALLINT    NOT NULL DEFAULT 0,
    last_login_time TIMESTAMP   NOT NULL DEFAULT now(),
    type            SMALLINT    NOT NULL DEFAULT 2,
    reason          VARCHAR(512),
    group_id        BIGINT      NOT NULL,
    uid             VARCHAR(32) NOT NULL,
    create_by       VARCHAR(32),
    create_time     TIMESTAMP   NOT NULL,
    update_by       VARCHAR(32),
    update_time     TIMESTAMP   NOT NULL,
    deleted         SMALLINT    NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX index_mer_user_account on love.mer_user (account);
CREATE INDEX index_mer_user_group_id on love.mer_user (group_id);

--- love.mer_user_admin_personal_info
DROP TABLE IF EXISTS love.mer_user_admin_personal_info;
CREATE TABLE love.mer_user_admin_personal_info
(
    admin_id     BIGINT      NOT NULL PRIMARY KEY,
    first_name   VARCHAR(32) NOT NULL,
    last_name    VARCHAR(32) NOT NULL,
    title        VARCHAR(32) NOT NULL,
    birthday     date        NOT NULL,
    phone_number VARCHAR(16) NOT NULL,
    create_time  TIMESTAMP   NOT NULL,
    update_time  TIMESTAMP   NOT NULL
);

CREATE UNIQUE INDEX index_mer_user_personal_admin_id on love.mer_user_admin_personal_info (admin_id);

--- love.mer_user_admin_business_info
DROP TABLE IF EXISTS love.mer_user_admin_business_info;
CREATE TABLE love.mer_user_admin_business_info
(
    admin_id         BIGINT       NOT NULL PRIMARY KEY,
    biz_name         VARCHAR(32)  NOT NULL,
    biz_type         SMALLINT     NOT NULL,
    ownership        SMALLINT     NOT NULL,
    incor_date       date         NOT NULL,
    website          VARCHAR(128) NOT NULL,
    biz_phone_number VARCHAR(16)  NOT NULL,
    country          VARCHAR(32)  NOT NULL,
    state            SMALLINT     NOT NULL,
    city             VARCHAR(32)  NOT NULL,
    zip_code         VARCHAR(16)  NOT NULL,
    address          VARCHAR(128) NOT NULL,
    create_time      TIMESTAMP    NOT NULL,
    update_time      TIMESTAMP    NOT NULL
);

CREATE UNIQUE INDEX index_mer_user_business_admin_id on love.mer_user_admin_business_info (admin_id);


--- love.mer_user_admin_invitation
DROP TABLE IF EXISTS love.mer_user_admin_invitation;
CREATE TABLE love.mer_user_admin_invitation
(
    id                  SERIAL        NOT NULL PRIMARY KEY,
    biz_name            VARCHAR(128)  NOT NULL,
    email               VARCHAR(32)   NOT NULL,
    code                CHAR(6)       NOT NULL,
    status              SMALLINT      NOT NULL DEFAULT 0,
    commission_fee_rate DECIMAL(4, 2) NOT NULL DEFAULT 0,
    mpa                 VARCHAR(255)  NOT NULL,
    create_by           VARCHAR(32),
    create_time         TIMESTAMP     NOT NULL,
    update_by           VARCHAR(32),
    update_time         TIMESTAMP     NOT NULL
);

CREATE UNIQUE INDEX index_merchant_admin_biz_name on love.mer_user_admin_invitation (biz_name);
CREATE UNIQUE INDEX index_merchant_admin_code on love.mer_user_admin_invitation (code);

--- love.mer_user_admin_commission_fee_rate
DROP TABLE IF EXISTS love.mer_user_admin_commission_fee_rate;
CREATE TABLE love.mer_user_admin_commission_fee_rate
(
    id             SERIAL        NOT NULL PRIMARY KEY,
    admin_id       BIGINT        NOT NULL,
    biz_name       VARCHAR(32)   NOT NULL,
    rate           DECIMAL(4, 2) NOT NULL DEFAULT 0,
    effective_time timestamp     NOT NULL,
    create_time    timestamp     NOT NULL,
    update_time    timestamp     NOT NULL
);

CREATE INDEX index_merchant_admin_commission_fee_admin_id on love.mer_user_admin_commission_fee_rate (admin_id);
CREATE INDEX index_merchant_admin_commission_fee_admin_biz_name on love.mer_user_admin_commission_fee_rate (biz_name);

--- love.mer_role
DROP TABLE IF EXISTS love.mer_role;
CREATE TABLE love.mer_role
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    code        VARCHAR(16),
    name        VARCHAR(32) NOT NULL,
    group_id    BIGINT      NOT NULL,
    status      SMALLINT    NOT NULL DEFAULT 0,
    create_by   VARCHAR(32),
    create_time TIMESTAMP   NOT NULL,
    update_by   VARCHAR(32),
    update_time TIMESTAMP   NOT NULL
);

--- love.mer_perm
DROP TABLE IF EXISTS love.mer_perm;
CREATE TABLE love.mer_perm
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    title       VARCHAR(32) NOT NULL,
    pid         INT         NOT NULL,
    type        SMALLINT    NOT NULL,
    code        VARCHAR(64),
    icon        VARCHAR(32),
    path        VARCHAR(128),
    apis        VARCHAR(255),
    sort_num    INT         NOT NULL,
    create_by   VARCHAR(32),
    create_time TIMESTAMP   NOT NULL,
    update_by   VARCHAR(32),
    update_time TIMESTAMP   NOT NULL
);

--- love.mer_user_role
DROP TABLE IF EXISTS love.mer_user_role;
CREATE TABLE love.mer_user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL
);

--- love.mer_role_perm
DROP TABLE IF EXISTS love.mer_role_perm;
CREATE TABLE love.mer_role_perm
(
    role_id BIGINT NOT NULL,
    perm_id BIGINT NOT NULL
);

--- init data
INSERT INTO love.mer_perm
VALUES (1, 'Settings', -1, 1, NULL, 'SettingOutlined', '/settings', '', 1, '1', '2023-04-16 22:03:33.152', '1000', '2023-04-24 10:30:20.185');
INSERT INTO love.mer_perm
VALUES (2, 'User', 1, 2, NULL, NULL, '/settings/users/page', '', 4, '1', '2023-04-16 22:03:51.754', '1', '2023-04-19 17:27:18.718');
INSERT INTO love.mer_perm
VALUES (4, 'Role', 1, 2, NULL, NULL, '/settings/roles/page', '', 2, '1', '2023-04-16 22:04:14.109', '1000', '2023-04-22 18:15:47.011');
INSERT INTO love.mer_perm
VALUES (5, 'Perm', 1, 2, NULL, NULL, '/settings/perm/page', '', 1, '1', '2023-04-16 22:04:31.772', '1', '2023-04-16 22:04:31.772');
INSERT INTO love.mer_perm
VALUES (6, 'Products', -1, 2, NULL, 'HeartOutlined', '/products/page', '', 10, '1', '2023-04-16 22:05:19.406', '1', '2023-04-19 20:25:51.17');
INSERT INTO love.mer_perm
VALUES (10, 'Payment', -1, 2, NULL, 'WalletOutlined', '/payment/page', '', 6, '1', '2023-04-17 11:16:53.486', '1', '2023-04-18 20:02:02.144');
INSERT INTO love.mer_perm
VALUES (11, 'Orders', -1, 2, NULL, 'ShoppingCartOutlined', '/orders/page', '', 99, '1', '2023-04-19 14:50:28.545', '1', '2023-04-19 14:50:28.545');
INSERT INTO love.mer_perm
VALUES (13, 'After-Sales', 8, 2, NULL, 'StarOutlined', '/after-sales/page', '', 9, '1', '2023-04-19 22:53:40.499', '1', '2023-04-19 22:53:40.499');
INSERT INTO love.mer_perm
VALUES (15, 'Store', -1, 2, NULL, 'HomeOutlined', '/store', '', 3, '1000', '2023-04-20 17:39:59.274', '1000', '2023-04-24 10:13:11.603');
INSERT INTO love.mer_perm
VALUES (17, 'Order Details', 11, 2, NULL, NULL, '/orders/details', '/order/detail', 1, 'S1651574075462815745', '2023-05-03 07:30:39.957', 'S1651574075462815745', '2023-05-03 07:30:39.957');
INSERT INTO love.mer_perm
VALUES (18, 'Products Item', 6, 2, NULL, NULL, '/products/item', '', 1, 'S1651574075462815745', '2023-05-03 07:31:10.714', 'S1651574075462815745', '2023-05-03 07:31:10.715');
INSERT INTO love.mer_perm
VALUES (19, 'Shippo Redirect', 11, 2, NULL, NULL, '/shippo-oauth-redirect', '', 2, 'S1651574075462815745', '2023-05-03 07:31:41.604', 'S1651574075462815745', '2023-05-03 07:31:41.604');
INSERT INTO love.mer_perm
VALUES (20, 'Edit', 11, 3, 'ORDER_EDIT', NULL, NULL, NULL, 1, 'S1651574075462815745', '2023-05-03 07:35:42.961', 'S1651574075462815745', '2023-05-03 07:35:42.961');
