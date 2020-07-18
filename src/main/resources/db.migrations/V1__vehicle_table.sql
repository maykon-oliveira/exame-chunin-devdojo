CREATE SCHEMA IF NOT EXISTS exame_chunin;

create table exame_chunin.vehicle
(
    id bigserial not null,
    type character varying(100) not null,
    brand character varying(200) not null,
    model character varying(200) not null,
    year smallint not null,
    price real not null
);

create unique index vehicle_id_uindex
    on exame_chunin.vehicle (id);

alter table exame_chunin.vehicle
    add constraint vehicle_pk
        primary key (id);


