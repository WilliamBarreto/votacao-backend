package br.com.prova.votacao.controller.dto;

import br.com.prova.votacao.domain.Sessao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SessaoResposta", description = "Objeto utilizado para retornar uma Pauta")
public class SessaoDTO {

    @ApiModelProperty(value = "Identificador da Sessão", example = "1")
    private final Long id;
    @ApiModelProperty(value = "Identificador da Pauta", example = "1")
    private final Long idPauta;
    @ApiModelProperty(value = "Duração em minutos da Sessão", example = "1")
    private final Integer duracaoEmMinutos;
    @ApiModelProperty(value = "Situação da Sessão [\"NAO_INCIADA\",\"ABERTA\"]", example = "NAO_INICIADA")
    private final String situacao;

    public SessaoDTO(Sessao sessao) {
        this.id = sessao.getId();
        this.idPauta = sessao.getPauta().getId();
        this.duracaoEmMinutos = sessao.getDuracaoEmMinutos();
        this.situacao = sessao.getSituacao().name();
    }

    public Long getId() {
        return id;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public Integer getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public String getSituacao() {
        return situacao;
    }
}
