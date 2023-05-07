create sequence hibernate_sequence start 2 increment 1;

create table messages (
    id bigserial not null,
    filename varchar(255),
    tag varchar(255),
    text varchar(2048)  not null,
    user_id bigint,
    primary key (id)
);

create table user_roles (
    user_id bigint not null,
    roles varchar(255)
);

create table users (
    id bigserial not null,
    activation_code varchar(255),
    email varchar(255),
    is_active boolean not null,
    password varchar(255)  not null,
    username varchar(255)  not null,
    primary key (id)
);

alter table if exists messages
    add constraint message_user_fk
    foreign key (user_id) references users;

alter table if exists user_roles
    add constraint user_roles_user_fk
    foreign key (user_id) references users;