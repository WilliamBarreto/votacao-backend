insert into votacao_db.pauta
  (id, nome,                       descricao)
values
  (1, 'Aprovacao de PLR',          'Pauta para aprovacao de PLR'),
  (2, 'Pauta sessao nao iniciada', 'Pauta para aprovacao de PLR'),
  (3, 'Pauta sessao fechada',      'Pauta com sessao fechada');


insert into votacao_db.sessao
  (id,       pauta_id,       duracao_em_minutos,       situacao_id,       data_abertura,       data_fechamento)
values
  (1,        2,              1,                        0,                 null,                null),
  (2,        3,              1,                        2,                 null,                null);




