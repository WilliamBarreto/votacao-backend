create table votacao_db.voto (
    id                  bigint not null auto_increment,
    sessao_id           bigint not null,
    cpf_associado       varchar(11) not null,
    voto                integer not null,
    constraint pk_voto primary key (id),
    constraint fk_voto_sessao_sessao_id foreign key (sessao_id) references votacao_db.sessao(id),
    constraint uk_voto_sessao_id_cpf_associado unique (sessao_id, cpf_associado)
);
