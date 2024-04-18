--add m_goods_sku default_sku column
ALTER TABLE "love"."m_goods_sku" ADD COLUMN "default_sku" smallint default 0;


--add love.m_category type,ids column
ALTER TABLE love.m_category
    ADD COLUMN type int NOT NULL DEFAULT 0,
    ADD COLUMN ids  VARCHAR(1000);

-- alter love.m_category alias column null
ALTER TABLE love.m_category alter column alias drop not null;

-- backup love.m_category
create table love.m_category_bak as select * from love.m_category;

-- backup love.m_goods
create table love.m_goods_bak as select * from love.m_goods;

delete from love.m_category;

-- init publishing category first level
INSERT INTO love.m_category(name,pid,level,status,sort_num,type) values
         ('Supplements', 0, 1, 1, 100, 0),
         ('Sports', 0, 1, 1, 101, 0),
         ('Bath', 0, 1, 1, 102, 0),
         ('Beauty', 0, 1, 1, 103, 0),
         ('Grocery', 0, 1, 1, 104, 0),
         ('Wearable', 0, 1, 1, 105, 0),
         ('LOVE accessories', 0, 1, 1, 106, 0);

-- init publishing category second level
INSERT INTO love.m_category(name,pid,level,status,sort_num,type) values
         ('5-HTP',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	1,	0	),
         ('Algae',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	2,	0	),
         ('Amino Acids',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	3,	0	),
         ('Antioxidants',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	4,	0	),
         ('Ashwagandha',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	5,	0	),
         ('Astaxanthin',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	6,	0	),
         ('Bee Propolis',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	7,	0	),
         ('Beta Glucan',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	8,	0	),
         ('Biotin',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	9,	0	),
         ('Bone & Joint',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	10,	0	),
         ('Calcium',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	11,	0	),
         ('Collagen',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	12,	0	),
         ('CoQ10',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	13,	0	),
         ('Curcumin',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	14,	0	),
         ('Enzymes',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	15,	0	),
         ('Fiber',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	16,	0	),
         ('Fish Oil',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	17,	0	),
         ('Flax Seed',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	18,	0	),
         ('Garlic',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	19,	0	),
         ('Ginkgo Biloba',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	20,	0	),
         ('Ginseng',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	21,	0	),
         ('Glucosamine',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	22,	0	),
         ('Greens & Superfoods',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	23,	0	),
         ('Hemp',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	24,	0	),
         ('Herbs',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	25,	0	),
         ('Homeopathy',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	26,	0	),
         ('Hyaluronic Acid',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	27,	0	),
         ('Krill Oil',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	28,	0	),
         ('Liposomal Vitamin C',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	29,	0	),
         ('Lutein',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	30,	0	),
         ('Maca',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	31,	0	),
         ('Magnesium',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	32,	0	),
         ('Meal Replacements',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	33,	0	),
         ('Milk Thistle (Silymarin)',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	34,	0	),
         ('Minerals',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	35,	0	),
         ('Multivitamins',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	36,	0	),
         ('Mushrooms',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	37,	0	),
         ('Omega 3 6 9',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	38,	0	),
         ('Plant Based Protein',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	39,	0	),
         ('PQQ',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	40,	0	),
         ('Probiotics',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	41,	0	),
         ('Quercetin',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	42,	0	),
         ('Red Yeast Rice',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	43,	0	),
         ('Resveratrol',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	44,	0	),
         ('SAM-e',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	45,	0	),
         ('Saw Palmetto',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	46,	0	),
         ('Spirulina',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	47,	0	),
         ('St. John''s Wort',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	48,	0	),
         ('Vitamin B',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	49,	0	),
         ('Vitamin C',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	50,	0	),
         ('Vitamin D',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	51,	0	),
         ('Vitamin E',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	52,	0	),
         ('Zinc',	(select id from love.m_category where name ='Supplements' and type = 0 limit 1),	2,	1,	53,	0	),
         ('BCAA',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	1,	0	),
         ('Creatine',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	2,	0	),
         ('Hydration & Electrolytes',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	3,	0	),
         ('L-Arginine',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	4,	0	),
         ('L-Carnitine',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	5,	0	),
         ('L-Glutamine',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	6,	0	),
         ('MCT Oil',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	7,	0	),
         ('Sport Bars',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	8,	0	),
         ('Sport Multivitamins',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	9,	0	),
         ('Whey Protein',	(select id from love.m_category where name ='Sports' and type = 0 limit 1),	2,	1,	10,	0	),
         ('Aromatherapy',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	1,	0	),
         ('Bath & Shower',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	2,	0	),
         ('Body & Massage Oils',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	3,	0	),
         ('Face Masks & Hand Sanitizers',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	4,	0	),
         ('Hair Care',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	5,	0	),
         ('Lip Care',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	6,	0	),
         ('Lotion',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	7,	0	),
         ('Medicine Cabinet',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	8,	0	),
         ('Men''s Grooming',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	9,	0	),
         ('Oral Care',	(select id from love.m_category where name ='Bath' and type = 0 limit 1),	2,	1,	10,	0	),
         ('Cleanse, Tone & Scrub',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	1,	0	),
         ('Face Masks & Peels',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	2,	0	),
         ('Gift Sets',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	3,	0	),
         ('Moisturizers & Creams',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	4,	0	),
         ('K-Beauty',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	5,	0	),
         ('Lip Care',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	6,	0	),
         ('Makeup',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	7,	0	),
         ('Makeup Brushes & Tools',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	8,	0	),
         ('Treatments & Serums',	(select id from love.m_category where name ='Beauty' and type = 0 limit 1),	2,	1,	9,	0	),
         ('Baking & Flour',	(select id from love.m_category where name ='Grocery' and type = 0 limit 1),	2,	1,	1,	0	),
         ('Butters & Spreads',	(select id from love.m_category where name ='Grocery' and type = 0 limit 1),	2,	1,	2,	0	),
         ('Coconut Oil',	(select id from love.m_category where name ='Grocery' and type = 0 limit 1),	2,	1,	3,	0	),
         ('Coffee',	(select id from love.m_category where name ='Grocery' and type = 0 limit 1),	2,	1,	4,	0	),
         ('Honey & Sweeteners',	(select id from love.m_category where name ='Grocery' and type = 0 limit 1),	2,	1,	5,	0	),
         ('Nuts & Seeds',	(select id from love.m_category where name ='Grocery' and type = 0 limit 1),	2,	1,	6,	0	),
         ('Spices',	(select id from love.m_category where name ='Grocery' and type = 0 limit 1),	2,	1,	7,	0	),
         ('Tea',	(select id from love.m_category where name ='Grocery' and type = 0 limit 1),	2,	1,	8,	0	),
         ('SleepMask',	(select id from love.m_category where name ='Wearable' and type = 0 limit 1),	2,	1,	1,	0	),
         ('Earplug',	(select id from love.m_category where name ='Wearable' and type = 0 limit 1),	2,	1,	2,	0	),
         ('Blue Light Blocking Glasses',	(select id from love.m_category where name ='Wearable' and type = 0 limit 1),	2,	1,	3,	0	),
         ('Health Device',	(select id from love.m_category where name ='Wearable' and type = 0 limit 1),	2,	1,	4,	0	),
         ('LOVE',	(select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1),	2,	1,	1,	0	);


-- init display category first level
INSERT INTO love.m_category(name,pid,level,sort_num,status,type) values
        ('Body',0,1,1,1,1),
        ('Energy',0,1,2,1,1),
        ('Health',0,1,3,1,1),
        ('Merch',0,1,4,1,1),
        ('Nutrition',0,1,5,1,1),
        ('Sleep',0,1,6,1,1),
        ('Stress',0,1,7,1,1);

-- Body
INSERT INTO love.m_category(name,pid,level,sort_num,status,type) values
        ('Facial Masks',(select id from love.m_category where name='Body' and type =1),2,1,1,1),
        ('Hair Care',(select id from love.m_category where name='Body' and type =1),2,2,1,1),
        ('Oral Health',(select id from love.m_category where name='Body' and type =1),2,3,1,1),
        ('Serums',(select id from love.m_category where name='Body' and type =1),2,4,1,1),
        ('Shampoo',(select id from love.m_category where name='Body' and type =1),2,5,1,1),
        ('Soap',(select id from love.m_category where name='Body' and type =1),2,6,1,1);

-- Energy
INSERT INTO love.m_category(name,pid,level,sort_num,status,type) values
        ('Herbs',(select id from love.m_category where name='Energy' and type =1),2,1,1,1),
        ('Mushrooms',(select id from love.m_category where name='Energy' and type =1),2,2,1,1),
        ('Vitamins',(select id from love.m_category where name='Energy' and type =1),2,3,1,1);

-- Health
INSERT INTO love.m_category(name,pid,level,sort_num,status,type) values
        ('Fitness',(select id from love.m_category where name='Health' and type =1),2,1,1,1),
        ('Herbs',(select id from love.m_category where name='Health' and type =1),2,2,1,1),
        ('Hormone Health',(select id from love.m_category where name='Health' and type =1),2,3,1,1);

-- Nutrition
INSERT INTO love.m_category(name,pid,level,sort_num,status,type) values
        ('Healthy Food',(select id from love.m_category where name='Nutrition' and type =1),2,1,1,1),
        ('Probiotic',(select id from love.m_category where name='Nutrition' and type =1),2,2,1,1),
        ('Tea',(select id from love.m_category where name='Nutrition' and type =1),2,3,1,1);

-- Sleep
INSERT INTO love.m_category(name,pid,level,sort_num,status,type) values
        ('Mushrooms',(select id from love.m_category where name='Sleep' and type =1),2,1,1,1),
        ('Sleep Gummies',(select id from love.m_category where name='Sleep' and type =1),2,2,1,1);

-- Stress
INSERT INTO love.m_category(name,pid,level,sort_num,status,type) values
        ('Herbs',(select id from love.m_category where name='Stress' and type =1),2,1,1,1),
        ('Stress Beverage',(select id from love.m_category where name='Stress' and type =1),2,2,1,1),
        ('Stress Gummies',(select id from love.m_category where name='Stress' and type =1),2,3,1,1);


-- update goods category
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0 limit 1) where id=20154;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20155;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20137;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20136;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20138;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Probiotics' and type = 0  limit 1) where id=20143;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20180;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20149;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20182;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20181;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20038;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20024;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20035;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20033;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20025;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20027;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hydration & Electrolytes' and type = 0  limit 1) where id=20351;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Oral Care' and type = 0  limit 1) where id=20250;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20248;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20251;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20249;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20036;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20045;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20222;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20052;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20220;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20219;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20210;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20223;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20212;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20225;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20070;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20213;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20077;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20221;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20211;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20207;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20218;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20226;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20209;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20227;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20075;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20215;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20224;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20208;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20217;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20079;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20216;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20026;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20057;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20062;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Probiotics' and type = 0  limit 1) where id=20247;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Beauty' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Treatments & Serums' and type = 0  limit 1) where id=20153;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Health Device' and type = 0  limit 1) where id=20047;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Health Device' and type = 0  limit 1) where id=20043;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Baking & Flour' and type = 0  limit 1) where id=20141;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Baking & Flour' and type = 0  limit 1) where id=20139;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Baking & Flour' and type = 0  limit 1) where id=20140;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Baking & Flour' and type = 0  limit 1) where id=20142;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20127;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20125;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20121;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20122;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20128;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20123;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Beauty' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Treatments & Serums' and type = 0  limit 1) where id=20126;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Bath & Shower' and type = 0  limit 1) where id=20124;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20120;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20119;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Plant Based Protein' and type = 0  limit 1) where id=20129;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Hair Care' and type = 0  limit 1) where id=20171;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20051;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20087;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20083;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20086;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20061;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20071;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20030;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20092;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20059;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20080;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20076;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20090;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20082;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20233;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20234;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20237;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20040;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20013;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20231;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20053;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20230;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20064;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20088;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20085;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20068;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20034;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20078;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20094;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20093;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20055;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20042;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20091;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20235;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20095;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20236;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Greens & Superfoods' and type = 0  limit 1) where id=20089;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20229;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20005;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20282;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20279;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20278;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20277;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20280;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20281;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Health Device' and type = 0  limit 1) where id=20175;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Probiotics' and type = 0  limit 1) where id=20102;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Probiotics' and type = 0  limit 1) where id=20101;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Probiotics' and type = 0  limit 1) where id=20098;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Probiotics' and type = 0  limit 1) where id=20100;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Probiotics' and type = 0  limit 1) where id=20262;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20164;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20163;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20261;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20169;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20165;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20162;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20167;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20170;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20161;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='LOVE accessories' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='LOVE' and type = 0  limit 1) where id=20166;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Oral Care' and type = 0  limit 1) where id=20174;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Bath' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Oral Care' and type = 0  limit 1) where id=20173;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Sport Multivitamins' and type = 0  limit 1) where id=20145;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Sports' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Sport Multivitamins' and type = 0  limit 1) where id=20146;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Vitamin B' and type = 0  limit 1) where id=20116;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Collagen' and type = 0  limit 1) where id=20190;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Magnesium' and type = 0  limit 1) where id=20200;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Whey Protein' and type = 0  limit 1) where id=20196;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Collagen' and type = 0  limit 1) where id=20195;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20245;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20241;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20239;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20244;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20253;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20246;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20259;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20258;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20256;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20243;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20255;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20240;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20254;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20176;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20252;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20257;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Magnesium' and type = 0  limit 1) where id=20152;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Algae' and type = 0  limit 1) where id=20267;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Algae' and type = 0  limit 1) where id=20184;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Algae' and type = 0  limit 1) where id=20269;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Algae' and type = 0  limit 1) where id=20185;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Algae' and type = 0  limit 1) where id=20268;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Blue Light Blocking Glasses' and type = 0  limit 1) where id=20132;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Blue Light Blocking Glasses' and type = 0  limit 1) where id=20130;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='SleepMask' and type = 0  limit 1) where id=20135;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Earplug' and type = 0  limit 1) where id=20134;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Blue Light Blocking Glasses' and type = 0  limit 1) where id=20133;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Wearable' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Blue Light Blocking Glasses' and type = 0  limit 1) where id=20131;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20273;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20274;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20275;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Mushrooms' and type = 0  limit 1) where id=20276;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20014;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20019;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20012;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20010;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Supplements' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Herbs' and type = 0  limit 1) where id=20017;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Tea' and type = 0  limit 1) where id=20375;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20368;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20371;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20370;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20364;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20365;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20366;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20367;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20369;
update  love.m_goods set first_cate_id = (select id from love.m_category where name ='Grocery' and type = 0 limit 1), second_cate_id = (select id from love.m_category where name ='Nuts & Seeds' and type = 0  limit 1) where id=20372;

update love.m_goods set first_cate_id = 31,second_cate_id =38 where deleted = 0 and first_cate_id <31