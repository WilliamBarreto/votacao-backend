package br.com.prova.votacao.client;

import br.com.prova.votacao.IntegracaoTests;
import br.com.prova.votacao.client.dto.CpfStatusDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static br.com.prova.votacao.client.dto.StatusVoto.UNABLE_TO_VOTE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ServicoExternoClientTest extends IntegracaoTests {

    @Autowired
    private ServicoExternoClient client;

    @Test
    @Disabled
    public void deve_buscar_status_por_cpf() {
        String cpf ="57283218017";
        CpfStatusDTO statusDTO = client.consultaSituacaoCPF(cpf);
        assertThat(statusDTO.getStatus(), is(UNABLE_TO_VOTE));
    }

    @Test
    @Disabled
    public void deve_buscar_status_por_cpf_invalido() {
        String cpf ="12313218878787887";
        CpfStatusDTO status = client.consultaSituacaoCPF(cpf);
        assertThat(status.getStatus(), is("UNABLE_TO_VOTE"));
    }
}
