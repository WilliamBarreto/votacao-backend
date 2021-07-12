package br.com.prova.votacao.controller.form;

import br.com.prova.votacao.domain.OpcaoVoto;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.domain.Voto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Voto", description = "Objeto utilizado para inclusão de Voto")
public class VotoForm {

    @ApiModelProperty(value = "Identificador da Sessão", example = "1", required = true)
    @NotNull
    private Long idSessao;

    @ApiModelProperty(value = "CPF do Associado", example = "00000000000", required = true)
    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    @ApiModelProperty(value = "Voto[\"SIM\" | \"NAO\"]", example = "SIM", required = true)
    @NotNull
    private OpcaoVoto voto;

    public Voto toEntity() {
        return Voto.builder()
                .sessao(Sessao.builder().id(idSessao).build())
                .cpfAssociado(cpf)
                .voto(voto)
                .build();
    }
}
