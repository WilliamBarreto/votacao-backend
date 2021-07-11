package br.com.prova.votacao.controller.dto;

import br.com.prova.votacao.domain.Voto;

public class VotoDTO {

    private final Long id;
    private final Long idSessao;
    private final String cpf;
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
