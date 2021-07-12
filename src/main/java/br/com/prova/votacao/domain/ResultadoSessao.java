package br.com.prova.votacao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resultado_sessao", schema = "votacao_db")
public class ResultadoSessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne
    @JoinColumn(name="sessao_id")
    @NotNull
    private Sessao sessao;

    @Column(name="total_votos_sim")
    @NotNull
    @PositiveOrZero
    private Integer quantidadeVotosSim;

    @Column(name="total_votos_nao")
    @NotNull
    @PositiveOrZero
    private Integer quantidadeVotosNao;

}
