package br.com.prova.votacao.controller.form;

import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.domain.Sessao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoForm {

    @NotNull
    private Long idPauta;

    @Positive
    private Integer duracaoEmMinutos;

    public Sessao toEntity() {
        return Sessao.builder()
                .pauta(Pauta.builder().id(idPauta).build())
                .duracaoEmMinutos(duracaoEmMinutos)
                .build();
    }
}
