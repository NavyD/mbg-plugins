drop table if exists pms_product;
create table pms_product (
  id bigint unsigned not null auto_increment,
  brand_id bigint unsigned not null comment '品牌id',
  product_category_id bigint unsigned not null comment '品牌分类id',
  feight_template_id bigint unsigned not null comment '运费模版id',
  product_attribute_category_id bigint unsigned not null comment '品牌属性分类id',
  product_name varchar(64) not null comment '商品名称',
  pic varchar(255) comment '图片',
  product_sn varchar(64) not null comment '货号',
  is_deleted tinyint unsigned not null default 0 comment '删除状态：0->未删除；1->已删除',
  is_published tinyint unsigned not null default 0 comment '上架状态：0->下架；1->上架',
  is_fresh tinyint unsigned not null default 0 comment '新品状态:0->不是新品；1->新品',
  is_recommanded tinyint unsigned not null default 0 comment '推荐状态；0->不推荐；1->推荐',
  is_verrified tinyint unsigned not null default 0 comment '审核状态：0->未审核；1->审核通过',
  sort int unsigned not null comment '排序',
  sale int unsigned not null comment '销量',
  price decimal(10, 2) comment '价格',
  promotion_price decimal(10, 2) comment '促销价格',
  gift_growth int unsigned not null default 0 comment '赠送的成长值',
  gift_point int unsigned not null default 0 comment '赠送的积分',
  use_point_limit int unsigned comment '限制使用的积分数',
  sub_title varchar(255) comment '副标题',
  product_description text comment '商品描述',
  original_price decimal(10, 2) comment '市场价',
  stock int unsigned not null comment '库存',
  low_stock int unsigned comment '库存预警值',
  unit varchar(16) comment '单位',
  product_weight decimal(10, 2) comment '商品重量，默认为克',
  is_previewed tinyint unsigned not null default 0 comment '是否为预告商品：0->不是；1->是',
  service_ids varchar(64) comment '以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮',
  keywords varchar(255) comment '关键字',
  note varchar(255) comment '备注',
  album_pics varchar(255) comment '画册图片，连产品图片限制为5张，以逗号分割',
  detail_title varchar(255) comment '详情标题',
  detail_desc text comment '详情描述',
  detail_html text comment '产品详情网页内容',
  detail_mobile_html text comment '移动端网页详情',
  promotion_start_time datetime comment '促销开始时间',
  promotion_end_time datetime comment '促销结束时间',
  promotion_per_limit int unsigned comment '活动限购数量',
  promotion_status tinyint unsigned not null default 0 comment '促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购',
  product_category_name varchar(255) comment '产品分类名称',
  brand_name varchar(255) comment '品牌名称',
  gmt_create DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  gmt_modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
  primary key (id)
) COMMENT '商品表. 商品信息主要包括四部分：商品的基本信息、商品的促销信息、商品的属性信息、商品的关联，商品表是整个商品的基本信息部分。';

insert into
  `pms_product` (
    `id`,
    `brand_id`,
    `product_category_id`,
    `feight_template_id`,
    `product_attribute_category_id`,
    `product_name`,
    `pic`,
    `product_sn`,
    `is_deleted`,
    `is_published`,
    `is_fresh`,
    `is_recommanded`,
    `is_verrified`,
    `sort`,
    `sale`,
    `price`,
    `promotion_price`,
    `gift_growth`,
    `gift_point`,
    `use_point_limit`,
    `sub_title`,
    `product_description`,
    `original_price`,
    `stock`,
    `low_stock`,
    `unit`,
    `product_weight`,
    `is_previewed`,
    `service_ids`,
    `keywords`,
    `note`,
    `album_pics`,
    `detail_title`,
    `detail_desc`,
    `detail_html`,
    `detail_mobile_html`,
    `promotion_start_time`,
    `promotion_end_time`,
    `promotion_per_limit`,
    `promotion_status`,
    `product_category_name`,
    `brand_name`
  )
VALUES
  (
    '1',
    '1',
    '1',
    '1',
    '1',
    '小米10',
    'pic/a/234',
    'p_sn',
    '0',
    '0',
    '0',
    '0',
    '0',
    '1',
    '100',
    '3999.9',
    '3333.99',
    '0',
    '0',
    '0',
    '666',
    '牛逼',
    '4000',
    '100',
    '10',
    '台',
    '200',
    '0',
    '1,2',
    '10,a',
    '好',
    'pic/b/aa2342',
    'detail not display',
    'desc',
    '<html></html>',
    'mobile html',
    null,
    null,
    null,
    '0',
    '手机',
    '小米'
  );
