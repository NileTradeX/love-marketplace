--- love.sys_user

DROP TABLE IF EXISTS love.sys_user;
CREATE TABLE love.sys_user
(
    id              SERIAL      NOT NULL PRIMARY KEY,
    account         VARCHAR(32) NOT NULL,
    username        VARCHAR(32) NOT NULL,
    password        VARCHAR(64) NOT NULL,
    status          SMALLINT    NOT NULL DEFAULT 1,
    last_login_time TIMESTAMP   NOT NULL DEFAULT now(),
    type            SMALLINT    NOT NULL DEFAULT 2,
    uid             VARCHAR(32) NOT NULL,
    create_by       VARCHAR(32) NOT NULL,
    create_time     TIMESTAMP   NOT NULL,
    update_by       VARCHAR(32) NOT NULL,
    update_time     TIMESTAMP   NOT NULL,
    deleted         SMALLINT    NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX index_sys_user_email_deleted on love.sys_user (account, deleted);

--- love.sys_role

DROP TABLE IF EXISTS love.sys_role;
CREATE TABLE love.sys_role
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    code        VARCHAR(16),
    name        VARCHAR(32) NOT NULL,
    create_by   VARCHAR(32) NOT NULL,
    create_time TIMESTAMP   NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time TIMESTAMP   NOT NULL
);

--- love.sys_perm
DROP TABLE IF EXISTS love.sys_perm;
CREATE TABLE love.sys_perm
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
    create_by   VARCHAR(32) NOT NULL,
    create_time TIMESTAMP   NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time TIMESTAMP   NOT NULL
);

--- love.sys_user_role
DROP TABLE IF EXISTS love.sys_user_role;
CREATE TABLE love.sys_user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL
);


--- love.sys_role_perm
DROP TABLE IF EXISTS love.sys_role_perm;
CREATE TABLE love.sys_role_perm
(
    role_id BIGINT NOT NULL,
    perm_id BIGINT NOT NULL
);

--- init data
INSERT INTO love.sys_user (id, account, username, password, status, last_login_time, create_by, create_time, update_by, update_time, deleted, type, uid)
VALUES (1000, 'super', 'super', '$2a$10$xcuoDqEDm8cUw6quqw/ItO8yUKxm76GnE.sGP2blsa0e6ckRIV4XS', 1, now(), '', now(), '', now(), 0, 1, 'S1651536675961348098');

INSERT INTO love.sys_perm
VALUES (1, 'Settings', -1, 1, '', 'SettingOutlined', '/settings', '', 10, '1000', '2023-04-20 07:54:14.549205', '1000', '2023-04-20 16:14:55.524');
INSERT INTO love.sys_perm
VALUES (2, 'User', 1, 2, '', '', '/settings/users/page', '', 100, '1000', '2023-04-20 07:54:14.549205', '1000', '2023-04-20 16:11:48.112');
INSERT INTO love.sys_perm
VALUES (3, 'Role', 1, 2, '', '', '/settings/roles/page', '', 100, '1000', '2023-04-20 07:54:14.549205', '1000', '2023-04-20 16:11:56.25');
INSERT INTO love.sys_perm
VALUES (4, 'Menu', 1, 2, '', '', '/settings/menus/page', '', 100, '1000', '2023-04-20 07:54:14.549205', '1000', '2023-04-20 16:11:51.117');
INSERT INTO love.sys_perm
VALUES (5, 'Customers', -1, 2, NULL, 'ContactsOutlined', '/customers/page', '', 100, '1000', '2023-04-20 15:54:25.928', '1000', '2023-04-20 16:18:31.555');
INSERT INTO love.sys_perm
VALUES (6, 'Customer Details', 5, 2, NULL, NULL, '/customers/details', '', 1, '1000', '2023-04-20 15:55:45.797', '1000', '2023-04-20 15:55:45.797');
INSERT INTO love.sys_perm
VALUES (8, 'Merchants', -1, 1, NULL, 'ShopOutlined', '/merchants', '', 90, '1000', '2023-04-20 15:57:44.434', '1000', '2023-04-20 16:13:15.924');
INSERT INTO love.sys_perm
VALUES (9, 'Onboarding', 8, 2, NULL, NULL, '/merchants/invitation-create', '', 4, '1000', '2023-04-20 15:58:58.105', '1000', '2023-04-20 16:22:16.137');
INSERT INTO love.sys_perm
VALUES (10, 'Onboard List', 8, 2, NULL, NULL, '/merchants/invitation-history', '', 3, '1000', '2023-04-20 15:59:45.58', '1000', '2023-04-20 16:22:31.49');
INSERT INTO love.sys_perm
VALUES (11, 'Onboard Review', 8, 2, NULL, NULL, '/merchants/review', '', 2, '1000', '2023-04-20 16:00:16.517', '1000', '2023-04-20 16:22:40.631');
INSERT INTO love.sys_perm
VALUES (12, 'Merchants', 8, 2, NULL, NULL, '/merchants/page', '', 1, '1000', '2023-04-20 16:00:33.943', '1000', '2023-04-20 16:22:47.621');
INSERT INTO love.sys_perm
VALUES (13, 'Merchants Details', 8, 2, NULL, NULL, '/merchants/details', '', 1, '1000', '2023-04-20 16:01:06.494', '1000', '2023-04-20 16:01:06.494');
INSERT INTO love.sys_perm
VALUES (14, 'Products', -1, 1, NULL, 'DribbbleOutlined', '/products', '', 70, '1000', '2023-04-20 16:02:19.07', '1000', '2023-04-24 10:24:44.675');
INSERT INTO love.sys_perm
VALUES (15, 'Products', 14, 2, NULL, NULL, '/products/page', '', 3, '1000', '2023-04-20 16:02:42.631', '1000', '2023-04-20 16:23:00.34');
INSERT INTO love.sys_perm
VALUES (17, 'Review', 14, 2, NULL, NULL, '/products/review', '', 2, '1000', '2023-04-20 16:03:47.151', '1000', '2023-04-20 16:03:47.151');
INSERT INTO love.sys_perm
VALUES (18, 'Product Item', 14, 2, NULL, NULL, '/products/item', '', 1, '1000', '2023-04-20 16:04:33.609', '1000', '2023-04-20 16:04:33.609');
INSERT INTO love.sys_perm
VALUES (19, 'Orders', -1, 2, NULL, 'CopyOutlined', '/orders/page', '', 60, '1000', '2023-04-20 16:05:56.628', '1000', '2023-04-20 17:30:29.255');
INSERT INTO love.sys_perm
VALUES (21, 'Reviews', -1, 2, NULL, 'FolderViewOutlined', '/reviews/page', '', 40, '1000', '2023-04-20 16:07:54.676', '1000', '2023-04-21 06:19:56.595');
INSERT INTO love.sys_perm
VALUES (20, 'Order Details', 19, 2, NULL, NULL, '/orders/details', '', 1, '1000', '2023-04-20 16:06:16.529', '1000', '2023-04-24 18:32:43.372');
INSERT INTO love.sys_perm
VALUES (22, 'Perm', 1, 2, NULL, NULL, '/settings/perm/page', '', 1, '1000', '2023-04-20 16:12:07.523', '1000', '2023-04-20 16:12:07.523');
INSERT INTO love.sys_perm
VALUES (24, 'Commission', -1, 2, NULL, 'DollarOutlined', '/commission/page', '', 80, '1000', '2023-04-21 16:12:18.401', '1000', '2023-04-21 16:12:18.401');
