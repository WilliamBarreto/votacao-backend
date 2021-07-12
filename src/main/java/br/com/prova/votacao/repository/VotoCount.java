package br.com.prova.votacao.repository;

import br.com.prova.votacao.domain.OpcaoVoto;

public class VotoCount {

    private final OpcaoVoto voto;
    private final long total;

    public VotoCount(OpcaoVoto opcaoVoto, long total) {
        this.voto = opcaoVoto;
        this.total = total;
    }

    public OpcaoVoto getVoto() {
        return voto;
    }

    public long getTotal() {
        return total;
    }
}
