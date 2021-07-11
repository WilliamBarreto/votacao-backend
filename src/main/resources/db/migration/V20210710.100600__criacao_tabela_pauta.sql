create schema if not exists votacao_db;

create table votacao_db.pauta (
    id          bigint not null auto_increment,
    nome        varchar(200) not null,
    descricao   varchar(1000),
    primary key (id)
);