create table votacao_db.resultado_sessao (
    id                  bigint not null auto_increment,
    sessao_id           bigint not null,
    total_votos_sim     integer not null,
    total_votos_nao     integer not null,
    constraint pk_resultado_sessao primary key (id),
    constraint fk_resultado_sessao_sessao_id foreign key (sessao_id) references votacao_db.sessao(id),
    constraint uk_resultado_sessao_sessao_id unique (sessao_id)
);
