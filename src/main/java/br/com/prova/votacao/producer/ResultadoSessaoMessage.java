package br.com.prova.votacao.producer;

import br.com.prova.votacao.domain.ResultadoSessao;

public class ResultadoSessaoMessage {

    private final Long idPauta;
    private final Integer totalSim;
    private final Integer totalNao;

    public ResultadoSessaoMessage(ResultadoSessao resultadoSessao) {
        this.idPauta = resultadoSessao.getSessao().getPauta().getId();
        this.totalSim = resultadoSessao.getQuantidadeVotosSim();
        this.totalNao = resultadoSessao.getQuantidadeVotosNao();
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public Integer getTotalSim() {
        return totalSim;
    }

    public Integer getTotalNao() {
        return totalNao;
    }
}
