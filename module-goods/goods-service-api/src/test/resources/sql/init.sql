--- love.m_brand

DROP TABLE IF EXISTS love.m_brand;
CREATE TABLE love.m_brand
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    name        VARCHAR(64)  NOT NULL,
    logo        VARCHAR(255) NOT NULL,
    status      SMALLINT     NOT NULL DEFAULT 0,
    merchant_id INT          NOT NULL,
    create_time TIMESTAMP    NOT NULL,
    update_time TIMESTAMP    NOT NULL
);

--- love.m_label

DROP TABLE IF EXISTS love.m_label;
CREATE TABLE love.m_label
(
    id     SERIAL       NOT NULL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    type   SMALLINT     NOT NULL, -- 0 ingredient, 1: benefit
    status SMALLINT     NOT NULL DEFAULT 1
);


create unique index unique_index_goods_label on love.m_label (name, type);

--- love.m_category

DROP TABLE IF EXISTS love.m_category;
CREATE TABLE love.m_category
(
    id       SERIAL      NOT NULL PRIMARY KEY,
    name     VARCHAR(64) NOT NULL,
    icon     VARCHAR(255),
    pid      INT         NOT NULL DEFAULT 0,
    level    SMALLINT    NOT NULL DEFAULT 1,
    sort_num SMALLINT    NOT NULL DEFAULT 1,
    status   SMALLINT    NOT NULL DEFAULT 1
);


--- love.m_goods

DROP SEQUENCE IF EXISTS love.m_goods_id_seq;
CREATE SEQUENCE love.m_goods_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.m_goods;
CREATE TABLE love.m_goods
(
    id                         BIGINT        NOT NULL DEFAULT nextval('love.m_goods_id_seq'),
    title                      VARCHAR(120)  NOT NULL,
    sub_title                  VARCHAR(512)  NOT NULL,
    first_cate_id              INT           NOT NULL,
    second_cate_id             INT,
    category_id                INT           NOT NULL,
    affiliate_link             VARCHAR(255),
    merchant_id                INT           NOT NULL,
    brand_id                   INT           NOT NULL,
    -- ingredients
    key_ingredients            VARCHAR(255),                       -- 这是个标签数组, 标签id以逗号分隔
    ingredients_json           VARCHAR(1024),                      -- [{name:value},{}]
    -- description
    desc_text                  text          NOT NULL,
    desc_warnings              text          NOT NULL,
    intro                      text          NOT NULL,
    -- media
    main_images                VARCHAR(2040) NOT NULL,             --最多8张图片,每张图片key长度255
    white_bg_img               VARCHAR(255)  NOT NULL,             --一张图片key长度255
    detail_images              VARCHAR(12750),                     --最多50张图片key,每张长度255
    on_hand_stock              SMALLINT,
    sales_volume               SMALLINT,
    min_price                  DECIMAL(8, 2),
    max_price                  DECIMAL(8, 2),
    status                     SMALLINT      NOT NULL DEFAULT 0,   --五种状态, 0:草稿, 1:审核中, 2:审核驳回, 3:售卖中 4: 已下架
    shipping_rates_template_id INT,
    certifications             VARCHAR(255),
    community_score            SMALLINT      NOT NULL DEFAULT 100, --DEFAULT 100
    type                       SMALLINT      NOT NULL DEFAULT 0,   -- 0: physical, 1: digital
    --review
    review_comment             VARCHAR(512),
    review_status              SMALLINT      NOT NULL DEFAULT -1,  --三种状态 0:awaiting, 1:approved,2:rejected
    review_time                TIMESTAMP,
    submission_time            TIMESTAMP,
    love_score                 SMALLINT      NOT NULL DEFAULT 10,
    why_love                   VARCHAR(1024) NOT NULL,
    search_page_title          VARCHAR(70),
    search_meta_description    VARCHAR(320),
    create_by                  VARCHAR(32)   NOT NULL,
    create_time                TIMESTAMP     NOT NULL,
    update_by                  VARCHAR(32)   NOT NULL,
    update_time                TIMESTAMP     NOT NULL,
    deleted                    SMALLINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

create index index_goods_merchant_id on love.m_goods (merchant_id);
create index index_goods_brand_id on love.m_goods (brand_id);

--- love.m_goods_sku

DROP SEQUENCE IF EXISTS love.m_goods_sku_id_seq;
CREATE SEQUENCE love.m_goods_sku_id_seq START WITH 20000 INCREMENT BY 1 NO MAXVALUE CACHE 1;

DROP TABLE IF EXISTS love.m_goods_sku;
CREATE TABLE love.m_goods_sku
(
    id                   BIGINT        NOT NULL DEFAULT nextval('love.m_goods_sku_id_seq'),
    goods_id             INT           NOT NULL,
    merchant_id          INT           NOT NULL,
    attr_value_json      VARCHAR(512),                     -- [{id:, name:, value:,},{}]
    cover                VARCHAR(255),                     --首图
    img_list             VARCHAR(512),                     --key逗号分隔
    price                DECIMAL(8, 2) NOT NULL DEFAULT 0,
    on_hand_stock        INT           NOT NULL DEFAULT 0,
    available_stock      INT           NOT NULL DEFAULT 0,
    committed_stock      INT           NOT NULL DEFAULT 0,
    code                 VARCHAR(64),
    shipping_weight      DECIMAL(8, 2) NOT NULL DEFAULT 0,
    shipping_weight_unit VARCHAR(10)   NOT NULL,
    status               SMALLINT,
    gtin                 VARCHAR(16),
    mpn                  VARCHAR(255),
    create_by            VARCHAR(32)   NOT NULL,
    create_time          TIMESTAMP     NOT NULL,
    update_by            VARCHAR(32)   NOT NULL,
    update_time          TIMESTAMP     NOT NULL,
    deleted              SMALLINT      NOT NULL DEFAULT 0, -- 0:没有删除, 1:已删除
    PRIMARY KEY (id)
);

create index index_goods_sku_goods_id on love.m_goods_sku (goods_id);

--- love.m_attr_name

DROP TABLE IF EXISTS love.m_attr_name;
CREATE TABLE love.m_attr_name
(
    id   SERIAL       NOT NULL PRIMARY KEY,
    name VARCHAR(128) NOT NULL
);

create unique index unique_index_goods_attr_name on love.m_attr_name (name);


--- love.m_attr_value
DROP TABLE IF EXISTS love.m_attr_value;
CREATE TABLE love.m_attr_value
(
    id           SERIAL       NOT NULL PRIMARY KEY,
    attr_name_id INT          NOT NULL,
    value        VARCHAR(255) NOT NULL
);


create unique index unique_index_goods_attr_value on love.m_attr_value (attr_name_id, value);

--- love.m_goods_doc
DROP TABLE IF EXISTS love.m_goods_doc;
CREATE TABLE love.m_goods_doc
(
    goods_id     BIGINT       NOT NULL PRIMARY KEY,
    coa          VARCHAR(255),
    coa_status   SMALLINT DEFAULT 0,
    msds         VARCHAR(255),
    msds_status  SMALLINT DEFAULT 0,
    ppp          VARCHAR(255),
    ppp_status   SMALLINT DEFAULT 0,
    logfm        VARCHAR(255),
    logfm_status SMALLINT DEFAULT 0,
    rein         VARCHAR(255),
    rein_status  SMALLINT DEFAULT 0,
    create_time  TIMESTAMP(6) NOT NULL,
    update_time  TIMESTAMP(6) NOT NULL
);


--- init data




