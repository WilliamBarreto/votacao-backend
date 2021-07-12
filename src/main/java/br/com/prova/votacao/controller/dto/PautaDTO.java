package br.com.prova.votacao.controller.dto;

import br.com.prova.votacao.domain.Pauta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PautaResposta", description = "Objeto utilizado para retornar uma Pauta")
public class PautaDTO {

    @ApiModelProperty(value = "Identificador da Pauta")
    private final Long id;
    @ApiModelProperty(value = "Nome da Pauta")
    private final String nome;
    @ApiModelProperty(value = "Descrição da Pauta")
    private final String descricao;

    public PautaDTO(Pauta pauta) {
        this.id = pauta.getId();
        this.nome = pauta.getNome();
        this.descricao = pauta.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

}
