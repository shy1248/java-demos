-- user表
drop table if exists user;
create table user
(
    id   integer     not null primary key auto_increment comment '用户ID',
    name varchar(20) not null comment '用户姓名',
    age  int default 18 comment '用户年龄'
) comment '用户表';
-- 添加索引
create unique index user_id_uindex on user (id);
-- 插入数据
insert into user(name, age)
values ("Chris", 15);
insert into user(name, age)
values ("Linda", 18);
insert into user(name, age)
values ("Sara", 23);
insert into user(name, age)
values ("Tina", 33);
insert into user(name, age)
values ("Alex", 23);
insert into user(name, age)
values ("Emma", 27);
insert into user(name, age)
values ("Anne", 44);
insert into user(name, age)
values ("Cindy", 35);
insert into user(name, age)
values ("Grace", 33);
insert into user(name, age)
values ("Susan", 18);
insert into user(name, age)
values ("Anna", 19);
insert into user(name, age)
values ("Maggie", 20);
insert into user(name, age)
values ("Christian", 21);
insert into user(name, age)
values ("Annie", 22);
insert into user(name, age)
values ("Rebecca", 30);
insert into user(name, age)
values ("Andy", 38);
insert into user(name, age)
values ("Claire", 27);
insert into user(name, age)
values ("Vanessa", 32);
insert into user(name, age)
values ("Judy", 20);
insert into user(name, age)
values ("Catherine", 30);
insert into user(name, age)
values ("Jean", 18);
insert into user(name, age)
values ("Helen", 19);
insert into user(name, age)
values ("Christina", 31);
insert into user(name, age)
values ("Karen", 23);
insert into user(name, age)
values ("Elaine", 35);
insert into user(name, age)
values ("Nicole", 26);
insert into user(name, age)
values ("Margaret", 27);
insert into user(name, age)
values ("Julia", 29);
insert into user(name, age)
values ("Lucy", 14);
insert into user(name, age)
values ("Natalie", 24);
insert into user(name, age)
values ("Kate", 19);
insert into user(name, age)
values ("Olivia", 26);
insert into user(name, age)
values ("Sam", 35);
insert into user(name, age)
values ("Betty", 18);

-- bank转账Demo
drop table if exists account;
create table account
(
    id           varchar(16) not null primary key comment '账户ID',
    account_name varchar(20) not null comment '账户名称',
    balance      int         not null default 1000 comment '账户余额',
    updated      timestamp   NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP comment '账户更新时间，自动维护'
) comment '账户表';
-- 添加索引
create unique index account_id_uindex on account (id);
-- 添加数据
insert into account(id, account_name, balance)
values ('123456789', 'Tom', 1000);
insert into account(id, account_name, balance)
values ('234567891', 'Jerry', 2000);
insert into account(id, account_name, balance)
values ('345678912', 'Micky', 3000);

-- 转账日志表
drop table if exists remit_log;
create table remit_log
(
    id           integer     not null primary key auto_increment comment '日志ID',
    from_account varchar(16) not null comment '转账原用户ID',
    to_account   varchar(16) not null comment '转账目标用户ID',
    created      timestamp   NULL DEFAULT CURRENT_TIMESTAMP comment '转账时间'
) comment '转账日志表';
-- 添加索引
create unique index remt_log_id_uindex on remit_log (id);

-- 学生信息Demo
-- 老师表
drop table if exists teacher;
create table teacher
(
    id   int(10) primary key auto_increment comment '老师ID',
    name varchar(20) not null comment '老师姓名'
) comment '老师信息表';

insert into teacher
values (default, 'LiuHua');
insert into teacher
values (default, 'ZhangSheng');
insert into teacher
values (default, 'LiuYe');
insert into teacher
values (default, 'GuoFuCheng');

-- 学生表
drop table if exists student;
create table student
(
    id   int(10) primary key auto_increment comment '学生ID',
    name varchar(20) not null comment '学生姓名',
    age  int(3) comment '年龄',
    tid  int(10)     not null comment '任课老师ID',
    constraint fk_teacher foreign key (tid) references teacher (id) -- 外键约束，建议不要写，拖慢插入性能
) comment '学生信息表';

insert into student
values (default, 'WangHong', 12, 1);
insert into student
values (default, 'LiMing', 13, 2);
insert into student
values (default, 'ChenQiang', 12, 3);
insert into student
values (default, 'WangLei', 11, 4);
insert into student
values (default, 'GuoQingHua', 13, 4);
insert into student
values (default, 'LiPeng', 14, 3);
insert into student
values (default, 'HeYan', 14, 2);
insert into student
values (default, 'HeZiWei', 13, 1);
insert into student
values (default, 'PengGang', 12, 1);
insert into student
values (default, 'SheGuiWei', 11, 2);
insert into student
values (default, 'JiangYaJun', 13, 3);
insert into student
values (default, 'SuLei', 12, 4);
insert into student
values (default, 'SuMan', 11, 1);

