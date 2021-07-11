insert into votacao_db.pauta
  (id, nome,                       descricao)
values
  (1, 'Aprovação de PLR',          'Pauta para aprovação de PLR'),
  (2, 'Pauta sessão não iniciada', 'Pauta para aprovação de PLR'),
  (3, 'Pauta sessão fechada',      'Pauta com sessão fechada');


insert into votacao_db.sessao
  (id,       pauta_id,       duracao_em_minutos,       situacao_id,       data_abertura,       data_fechamento)
values
  (1,        2,              1,                        0,                 null,                null),
  (2,        3,              1,                        2,                 null,                null);




