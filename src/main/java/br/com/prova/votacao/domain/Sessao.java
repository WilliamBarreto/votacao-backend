package br.com.prova.votacao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessao", schema = "votacao_db")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne
    @JoinColumn(name="pauta_id")
    @NotNull
    private Pauta pauta;

    @Column(name="duracao_em_minutos")
    @NotNull
    @Min(value = 1)
    private Integer duracaoEmMinutos;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "situacao_id")
    @NotNull
    private SituacaoSessao situacao;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

}
