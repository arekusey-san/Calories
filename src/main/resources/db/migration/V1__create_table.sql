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

drop sequence if exists products_seq cascade;
create sequence products_seq start with 1 increment by 1;

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

drop sequence if exists user_log_seq cascade;
create sequence user_log_seq start with 1 increment by 1;

drop table if exists user_log cascade;
create table user_log
(
    id        bigint not null,
    action    varchar(255) check (action in ('ADD_PRODUCT', 'EDIT_PRODUCT', 'DELETE_PRODUCT', 'ADD_DISH', 'EDIT_DISH',
                                             'DELETE_DISH', 'ADD_USER', 'EDIT_USER', 'DELETE_USER', 'AUTHORIZE')),
    login     varchar(255),
    reason    text,
    timestamp varchar(255),
    primary key (id)
);