CREATE database demo; use demo; grant all
ON demo.* to 'demo'@'%' identified by '123456'; flush privileges;

CREATE TABLE `t_user` ( `id` int primary key auto_increment, `name` varchar(25) not null comment '用户名', `age` tinyint comment '用户年龄' ) comment '用户信息表';

INSERT INTO `t_user` (`name`, `age`) values ('赵启明', 18),('钱多多', 25),('孙晓丽', 32),('李小红', 16),('王大伟', 40);

CREATE TABLE `t_category` ( `id` int primary key auto_increment, `name` varchar(30) not null comment '商品类别' ) comment '商品类别表';

INSERT INTO `t_category` (`name`) values ('手机'),('电脑'),('家具'),('服装'),('图书');

CREATE TABLE `t_goods` ( `id` int primary key auto_increment, `name` varchar(50) not null comment '商品名称', `category_id` int not null comment '商品类别ID', `price` decimal(11,5) not null comment '商品价格' ) comment '商品表';

INSERT INTO `t_goods` (`name`, `category_id`, `price`) values ('iphone 8', 1, 3449), ('iphone 11 pro', 1, 5499), ('iphone 12', 1, 6099), ('Thinkpad x1', 2, 12999), ('Thinkpad T479', 2, 7999), ('MacBook Air', 2, 6999), ('MacBook pro', 2, 9999), ('亲友布艺沙发', 3, 1499), ('珞玲珑电视柜', 3, 899), ('卓览岩板茶几电视柜组合', 3, 1780), ('和大人熟胶凳子', 3, 28.8), ('艺柳园实木小板凳', 3, 68), ('艾星大理石茶几', 3, 1584.00), ('日着原创设计师黑色休闲裤女', 4, 289), ('朵沐缦英伦风哈伦裤', 4, 139), ('betu百图女装翻领牛仔外套', 4, 269.00), ('鄂尔多斯精纺羊绒POLO衫', 4, 1190.00), ('bosie衬衣外套', 4, 349.00), ('GF FERRE休闲格子衬衫', 4, 714.00), ('VIISHOW短袖衬衫', 4, 149.00), ('荒野求生少年生存小说系列', 5, 500), ('童立方·科学在闪耀：阿歇特儿童科普小百科（套装全8册）', 5, 89), ('中国历史常识', 5, 37.40), ('2021新高考数学真题全刷', 5, 79), ('Visual Studio Code 权威指南', 5, 49.5);
