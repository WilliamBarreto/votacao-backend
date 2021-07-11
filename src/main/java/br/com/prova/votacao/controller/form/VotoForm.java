package br.com.prova.votacao.controller.form;

import br.com.prova.votacao.domain.OpcaoVoto;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.domain.Voto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoForm {

    @NotNull
    private Long idSessao;

    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    private OpcaoVoto voto;

    public Voto toEntity() {
        return Voto.builder()
                .sessao(Sessao.builder().id(idSessao).build())
                .cpfAssociado(cpf)
                .voto(voto)
                .build();
    }
}
