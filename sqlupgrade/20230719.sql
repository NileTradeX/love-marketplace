ALTER TABLE "love"."mer_user_admin_business_info"
    ADD COLUMN "biz_order_mgmt_email" VARCHAR(32) NOT NULL DEFAULT '';

UPDATE love.mer_user_admin_business_info
SET biz_order_mgmt_email = love.mer_user.account
    FROM
	love.mer_user
WHERE love.mer_user_admin_business_info.admin_id = love.mer_user.id
  AND love.mer_user_admin_business_info.biz_order_mgmt_email = '';
