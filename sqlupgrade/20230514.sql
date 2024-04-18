truncate table love.m_category;

delete from love.m_label where type = 1;

update love.m_goods set first_cate_id = 1 ;

alter table love.m_goods alter column type set default 1;
update love.m_goods set type = 1 ;

alter table love.m_goods alter column on_hand_stock type int using on_hand_stock::int;

INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (1, 'Stress', 0, 1, 1, 1, 'dev/20230514/icon/20230514-170201.png');
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (2, 'Sleep', 0, 1, 1, 1, 'dev/20230514/icon/20230514-170212.png');
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (3, 'Energy', 0, 1, 1, 1, 'dev/20230514/icon/20230514-170207.png');
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (4, 'Body', 0, 1, 1, 1, 'dev/20230514/icon/20230514-170217.png');
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (5, 'Nutrition', 0, 1, 1, 1, 'dev/20230514/icon/20230514-170147.png');
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (6, 'Health', 0, 1, 1, 1, 'dev/20230514/icon/20230514-170155.png');
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (7, 'Merch', 0, 1, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (8, 'Stress Gummies', 1, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (9, 'Essential Oils', 1, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (10, 'Stress Beverage', 1, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (11, 'Sleep Gummies', 2, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (12, 'Melatonin', 2, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (13, 'Sleep Oils', 2, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (14, 'B12', 3, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (15, 'Energy Bar', 3, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (16, 'Energy Beverage', 3, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (17, 'Shampoo', 4, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (18, 'Soap', 4, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (19, 'Lotion', 4, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (20, 'Oral Health', 4, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (21, 'Probiotic', 5, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (22, 'Vitamin', 5, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (23, 'Tea', 5, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (24, 'Health Food', 5, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (25, 'Hormone Health', 6, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (26, 'Fitness', 6, 2, 1, 1, null);
INSERT INTO love.m_category (id, name, pid, level, sort_num, status, icon) VALUES (27, 'Electrolytes', 6, 2, 1, 1, null);
