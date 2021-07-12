package br.com.prova.votacao.controller.dto;

import br.com.prova.votacao.domain.Voto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "VotoResposta", description = "Objeto utilizado para retornar uma Pauta")
public class VotoDTO {

    @ApiModelProperty(value = "Identificador do Voto", example = "1")
    private final Long id;
    @ApiModelProperty(value = "Identificador da Sessao", example = "1")
    private final Long idSessao;
    @ApiModelProperty(value = "CPF do Associado", example = "00000000000")
    private final String cpf;
    @ApiModelProperty(value = "Voto[\"NAO\" | \"SIM\"]", example = "SIM")
    private final String voto;

    public VotoDTO(Voto voto) {
        this.id = voto.getId();
        this.idSessao = voto.getSessao().getId();
        this.cpf = voto.getCpfAssociado();
        this.voto = voto.getVoto().toString();
    }

    public Long getId() {
        return id;
    }

    public Long getIdSessao() {
        return idSessao;
    }

    public String getCpf() {
        return cpf;
    }

    public String getVoto() {
        return voto;
    }
}
