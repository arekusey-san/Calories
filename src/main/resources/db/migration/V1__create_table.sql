drop sequence if exists dishes_seq cascade;
create sequence dishes_seq start with 1 increment by 1;

drop table if exists dishes cascade;
create table dishes
(
    id            bigint       not null,
    calories      float(53)    not null,
    carbohydrates float(53)    not null,
    fats          float(53)    not null,
    name          varchar(255) not null,
    proteins      float(53)    not null,
    primary key (id)
);

drop sequence if exists product_seq cascade;
create sequence product_seq start with 1 increment by 1;

drop table if exists products cascade;
create table products
(
    id            bigint       not null,
    calories      float(53)    not null,
    carbohydrates float(53)    not null,
    fats          float(53)    not null,
    name          varchar(255) not null,
    proteins      float(53)    not null,
    primary key (id)
);

drop sequence if exists user_seq cascade;
create sequence user_seq start with 1 increment by 1;

drop table if exists users cascade;
create table users
(
    id          bigint       not null,
    archive     boolean      not null,
    email       varchar(255) not null,
    lastname    varchar(255) not null,
    login       varchar(255),
    middle_name varchar(255),
    name        varchar(255),
    password    varchar(255),
    phone       varchar(255),
    role        varchar(255) check (role in ('USER', 'MANAGER', 'ADMIN')),
    sex         smallint     not null,
    primary key (id)
);
