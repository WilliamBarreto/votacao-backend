package br.com.prova.votacao.client.dto;

import lombok.Data;

@Data
public class CpfStatusDTO {

    private StatusVoto status;

    public CpfStatusDTO() {
    }

    public CpfStatusDTO(StatusVoto status) {
        this.status = status;
    }
}
