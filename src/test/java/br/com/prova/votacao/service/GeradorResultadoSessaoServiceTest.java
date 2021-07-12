package br.com.prova.votacao.service;

import br.com.prova.votacao.IntegracaoTests;
import br.com.prova.votacao.domain.ResultadoSessao;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.producer.SessaoEncerradaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

public class GeradorResultadoSessaoServiceTest extends IntegracaoTests {

    @Autowired
    private GeradorResultadoSessaoService service;

    @MockBean
    private SessaoEncerradaProducer sessaoEncerradaProducer;

    @Test
    public void deve_gerar_resultado() {
        // given
        doNothing().when(sessaoEncerradaProducer).sendMessage(any());
        Sessao sessao = Sessao.builder().id(3L).build();
        // when
        ResultadoSessao resultadoSessao = service.gerar(sessao);
        // then
        assertThat(resultadoSessao.getId(), notNullValue());
        assertThat(resultadoSessao.getSessao().getId(), is(3L));
        assertThat(resultadoSessao.getQuantidadeVotosNao(), is(0));
        assertThat(resultadoSessao.getQuantidadeVotosSim(), is(0));
    }

}
