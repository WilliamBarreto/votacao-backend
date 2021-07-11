package br.com.prova.votacao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voto", schema = "votacao_db")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "cpf_associado")
    @NotNull
    @Size(min = 11, max = 11)
    private String cpfAssociado;

    @ManyToOne
    @JoinColumn(name = "sessao_id")
    @NotNull
    private Sessao sessao;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "voto")
    @NotNull
    private OpcaoVoto voto;
}
