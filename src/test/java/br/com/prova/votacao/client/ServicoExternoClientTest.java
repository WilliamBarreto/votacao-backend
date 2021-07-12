package br.com.prova.votacao.client;

import br.com.prova.votacao.IntegracaoTests;
import br.com.prova.votacao.client.dto.CpfStatusDTO;
import br.com.prova.votacao.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static br.com.prova.votacao.client.dto.StatusVoto.ABLE_TO_VOTE;
import static br.com.prova.votacao.client.dto.StatusVoto.UNABLE_TO_VOTE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ServicoExternoClientTest extends IntegracaoTests {

    public static final String CPF = "57283218017";
    public static final String CPF_INVALIDO = "12313218878787887";
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ServicoExternoClient client;

    @Test
    public void deve_buscar_status_por_cpf_able_to_vote() {
        // given
        CpfStatusDTO status = new CpfStatusDTO(ABLE_TO_VOTE);
        when(restTemplate.getForObject("https://user-info.herokuapp.com/users/" + CPF, CpfStatusDTO.class)).thenReturn(status);
        // when
        CpfStatusDTO statusDTO = client.consultaSituacaoCPF(CPF);
        // then
        assertThat(statusDTO.getStatus(), is(ABLE_TO_VOTE));
    }

    @Test
    public void deve_buscar_status_por_cpf_unable_to_vote() {
        // given
        CpfStatusDTO status = new CpfStatusDTO(UNABLE_TO_VOTE);
        when(restTemplate.getForObject("https://user-info.herokuapp.com/users/" + CPF, CpfStatusDTO.class)).thenReturn(status);
        // when
        CpfStatusDTO statusDTO = client.consultaSituacaoCPF(CPF);
        // then
        assertThat(statusDTO.getStatus(), is(UNABLE_TO_VOTE));
    }

    @Test
    public void deve_buscar_status_por_cpf_invalido() {
        // given
        when(restTemplate.getForObject("https://user-info.herokuapp.com/users/" + CPF_INVALIDO, CpfStatusDTO.class)).thenThrow(HttpClientErrorException.NotFound.class);
        // when
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> client.consultaSituacaoCPF(CPF_INVALIDO));
        // then
        assertThat(exception.getMessage(), is("CPF 12313218878787887 inválido."));
    }
}
