create table exame_chunin.logged_user
(
    id          bigserial    not null
        constraint user_pk
            primary key,
    username    varchar(150) not null,
    password    varchar(150) not null,
    authorities varchar(150) not null
);