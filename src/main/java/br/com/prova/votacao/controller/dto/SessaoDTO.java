package br.com.prova.votacao.controller.dto;

import br.com.prova.votacao.domain.Sessao;

public class SessaoDTO {

    private final Long id;
    private final Long idPauta;
    private final Integer duracaoEmMinutos;
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
