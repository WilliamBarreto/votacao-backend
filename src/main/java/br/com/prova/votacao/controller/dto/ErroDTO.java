package br.com.prova.votacao.controller.dto;

public class ErroDTO {

    private String messagem;

    public ErroDTO(String messagem) {
        this.messagem = messagem;
    }

    public String getMessagem() {
        return messagem;
    }
}
