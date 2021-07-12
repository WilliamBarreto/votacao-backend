package br.com.prova.votacao.controller.form;

import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.domain.Sessao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Sessao", description = "Objeto utilizado para inclusão de Sessao")
public class SessaoForm {

    @ApiModelProperty(value = "Identificador da pauta", example = "1", required = true)
    @NotNull
    private Long idPauta;

    @ApiModelProperty(value = "Duração em Minutos", example = "1")
    @Positive
    private Integer duracaoEmMinutos;

    public Sessao toEntity() {
        return Sessao.builder()
                .pauta(Pauta.builder().id(idPauta).build())
                .duracaoEmMinutos(duracaoEmMinutos)
                .build();
    }
}
