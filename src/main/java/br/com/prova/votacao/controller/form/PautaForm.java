package br.com.prova.votacao.controller.form;

import br.com.prova.votacao.domain.Pauta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Pauta", description = "Objeto utilizado para inclusão de Pauta")
public class PautaForm {

    @ApiModelProperty(value = "Nome da pauta", example = "", required = true)
    @NotBlank
    @Size(max = 200)
    private String nome;

    @ApiModelProperty(value = "Descrição da pauta", example = "")
    @Size(min = 1, max = 1000)
    private String descricao;


    public Pauta toEntity() {
        return Pauta.builder().nome(nome).descricao(descricao).build();
    }
}
