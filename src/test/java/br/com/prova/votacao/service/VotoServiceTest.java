package br.com.prova.votacao.service;

import br.com.prova.votacao.IntegracaoTests;
import br.com.prova.votacao.client.ServicoExternoClient;
import br.com.prova.votacao.client.dto.CpfStatusDTO;
import br.com.prova.votacao.client.dto.StatusVoto;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.domain.Voto;
import br.com.prova.votacao.exception.ValidacaoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.prova.votacao.domain.OpcaoVoto.SIM;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class VotoServiceTest extends IntegracaoTests {

    @Autowired
    private VotoService service;

    @MockBean
    private ServicoExternoClient client;

    @Test
    public void deve_incluir_pauta() {
        // given
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.ABLE_TO_VOTE));
        Voto voto = Voto.builder()
                .sessao(Sessao.builder().id(3L).build())
                .cpfAssociado("00000000000")
                .voto(SIM)
                .build();
        // when
        Voto votoIncluido = service.incluir(voto);
        // then
        assertNotNull((votoIncluido.getId()));
        assertThat(voto.getSessao().getId(), is(3L));
        assertThat(voto.getVoto(), is(SIM));
    }

    @Test
    public void deve_nao_incluir_pauta_quando_sessao_nao_encontrada() {
        // given
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.ABLE_TO_VOTE));
        Voto voto = Voto.builder()
                .sessao(Sessao.builder().id(Long.MAX_VALUE).build())
                .cpfAssociado("00000000000")
                .voto(SIM)
                .build();
        // when
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> service.incluir(voto));
        // then
        assertThat(exception.getMessage(), is("Sessão 9223372036854775807 não encontrado"));
    }

    @Test
    public void deve_nao_incluir_pauta_quando_servico_extorno_retorna_unable_to_vote() {
        // given
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.UNABLE_TO_VOTE));
        Voto voto = Voto.builder()
                .sessao(Sessao.builder().id(3L).build())
                .cpfAssociado("00000000000")
                .voto(SIM)
                .build();
        // when
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> service.incluir(voto));
        // then
        assertThat(exception.getMessage(), is("Usuário não possui permissão para executar operação"));
    }
}
