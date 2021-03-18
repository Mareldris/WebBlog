create table sequence_post
(
    next_val bigint  not null
);

create table sequence_user
(
    next_val bigint  not null
);

create table post
(
    id        bigint       not null
        primary key,
    anons     varchar(255),
    filename  varchar(255),
    full_text varchar(2048),
    title     varchar(255),
    views     int          not null,
    user_id   bigint       not null
);

    create table user
(
    id              bigint       not null
        primary key,
    activation_code varchar(255),
    active          bit          not null,
    email           varchar(255),
    password        varchar(255),
    username        varchar(255)
);

create table user_role
(
    user_id bigint       not null,
    roles   varchar(255)
);



alter table post
    add constraint message_user_from_key
    foreign key (user_id)
    references user (id);

alter table user_role
    add constraint message_user_role_from_key
    foreign key (user_id)
    references user (id);