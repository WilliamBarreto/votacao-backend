package br.com.prova.votacao.service;

import br.com.prova.votacao.IntegracaoTests;
import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.domain.SituacaoSessao;
import br.com.prova.votacao.exception.ValidacaoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static br.com.prova.votacao.domain.SituacaoSessao.NAO_INICIADA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessaoServiceTest extends IntegracaoTests {

    @Autowired
    private SessaoService service;

    @Test
    public void deve_incluir_sessao() {
        // given
        Sessao sessao = Sessao.builder()
                .pauta(Pauta.builder().id(1L).build())
                .duracaoEmMinutos(3)
                .build();
        // when
        Sessao sessaoIncluida = service.incluir(sessao);
        // then
        assertNotNull((sessaoIncluida.getId()));
        assertThat(sessaoIncluida.getDuracaoEmMinutos(), is(3));
        assertThat(sessaoIncluida.getPauta().getId(),    is(1L));
        assertThat(sessaoIncluida.getSituacao(),         is(NAO_INICIADA));
    }

    @Test
    public void deve_incluir_sessao_com_duracao_em_minutos_um_quando_nao_informado() {
        // given
        Sessao sessao = Sessao.builder().pauta(Pauta.builder().id(1L).build()).build();
        // when
        Sessao sessaoIncluida = service.incluir(sessao);
        // then
        assertNotNull((sessaoIncluida.getId()));
        assertThat(sessaoIncluida.getDuracaoEmMinutos(), is(1));

    }

    @Test
    public void deve_incluir_sessao_com_situacao_nao_iniciada() {
        // given
        Sessao sessao = Sessao.builder().pauta(Pauta.builder().id(1L).build()).situacao(SituacaoSessao.ABERTA).build();
        // when
        Sessao sessaoIncluida = service.incluir(sessao);
        // then
        assertNotNull((sessaoIncluida.getId()));
        assertThat(sessaoIncluida.getSituacao(),         is(NAO_INICIADA));
    }

    @Test
    public void deve_nao_incluir_sessao_com_pauta_nao_cadastrado() {
        // given
        Sessao sessao = Sessao.builder().pauta(Pauta.builder().id(Long.MAX_VALUE).build()).situacao(SituacaoSessao.ABERTA).build();
        // when
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> service.incluir(sessao));
        // then
        assertThat(exception.getMessage(), is("Pauta 9223372036854775807 não encontrado"));
    }

}
