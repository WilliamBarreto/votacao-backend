package br.com.prova.votacao.controller.dto;

public class PropriedadeErroDTO extends ErroDTO {

    private final String propriedade;

    public PropriedadeErroDTO(String nomeCampo, String messagem) {
        super(messagem);
        this.propriedade = nomeCampo;
    }

    public String getPropriedade() {
        return propriedade;
    }
}
