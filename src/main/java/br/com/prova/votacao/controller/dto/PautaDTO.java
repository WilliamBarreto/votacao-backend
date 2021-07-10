package br.com.prova.votacao.controller.dto;

import br.com.prova.votacao.domain.Pauta;

public class PautaDTO {

    private final Long id;
    private final String nome;
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
