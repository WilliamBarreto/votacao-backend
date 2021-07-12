insert into votacao_db.pauta
  (id, nome,                       descricao)
values
  (1, 'Aprovacao de PLR',          'Pauta para aprovacao de PLR'),
  (2, 'Pauta sessao nao iniciada', 'Pauta para aprovacao de PLR'),
  (3, 'Pauta sessao fechada',      'Pauta com sessao fechada'),
  (4, 'Pauta sessao aberta',       'Pauta com sessao aberta');


insert into votacao_db.sessao
  (id,       pauta_id,       duracao_em_minutos,       situacao_id,       data_abertura,            data_fechamento)
values
  (1,        2,              1,                        0,                 null,                     null),
  (2,        3,              1,                        2,                 '2011-12-18 13:17:17',    '2011-12-18 13:17:17'),
  (3,        4,              60,                       1,                 CURRENT_TIMESTAMP(),      null);



