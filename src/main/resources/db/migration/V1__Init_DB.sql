create table sequence_post
(
    next_val bigint null
);

create table sequence_user
(
    next_val bigint null
);

create table post
(
    id        bigint       not null
        primary key,
    anons     varchar(255) null,
    filename  varchar(255) null,
    full_text varchar(2048) null,
    title     varchar(255) null,
    views     int          not null,
    user_id   bigint       null
);

    create table user
(
    id              bigint       not null
        primary key,
    activation_code varchar(255) null,
    active          bit          not null,
    email           varchar(255) null,
    password        varchar(255) null,
    username        varchar(255) null
);

create table user_role
(
    user_id bigint       not null,
    roles   varchar(255) null
);

