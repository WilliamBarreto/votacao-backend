package br.com.prova.votacao.service;

import br.com.prova.votacao.IntegracaoTests;
import br.com.prova.votacao.domain.ResultadoSessao;
import br.com.prova.votacao.domain.Sessao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GeradorResultadoSessaoServiceTest extends IntegracaoTests {

    @Autowired
    private GeradorResultadoSessaoService service;

    @Test
    public void deve_gerar_resultado() {
        Sessao sessao = Sessao.builder().id(3L).build();
        ResultadoSessao resultadoSessao = service.gerar(sessao);
        assertThat(resultadoSessao.getId(), notNullValue());
        assertThat(resultadoSessao.getSessao().getId(), is(3L));
        assertThat(resultadoSessao.getQuantidadeVotosNao(), is(0));
        assertThat(resultadoSessao.getQuantidadeVotosSim(), is(0));
    }

}
