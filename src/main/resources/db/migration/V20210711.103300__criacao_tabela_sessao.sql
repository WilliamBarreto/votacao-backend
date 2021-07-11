create table votacao_db.sessao (
    id                  bigint not null auto_increment,
    pauta_id            bigint not null,
    duracao_em_minutos  integer not null,
    situacao_id         integer not null,
    data_abertura       datetime,
    data_fechamento     datetime,
    constraint pk_sessao primary key (id),
    constraint fk_sessao_pauta_pauta_id foreign key (pauta_id) references votacao_db.pauta(id),
    constraint uk_sessao_pauta_id unique (pauta_id)
);