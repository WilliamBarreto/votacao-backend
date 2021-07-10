package br.com.prova.votacao.controller.form;

import br.com.prova.votacao.domain.Pauta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaForm {

    @NotBlank
    @Size(max = 200)
    private String nome;

    @Size(min = 1, max = 1000)
    private String descricao;


    public Pauta toEntity() {
        return Pauta.builder().nome(nome).descricao(descricao).build();
    }
}
