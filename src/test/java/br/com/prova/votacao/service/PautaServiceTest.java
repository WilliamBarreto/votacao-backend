package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PautaServiceTest {

    @Autowired
    private PautaService service;

    @Test
    public void deve_incluir_pauta() {
        Pauta pauta = Pauta.builder().nome("Pauta").descricao("Descrição").build();
        Pauta pautaIncluida = service.incluir(pauta);
        assertNotNull((pautaIncluida.getId()));
    }

    @Test
    public void deve_buscar_por_id() {
        Pauta pauta = service.buscarPorId(1L);
        assertThat(pauta.getId(), is(1L));
        assertThat(pauta.getNome(), is("Aprovação de PLR"));
        assertThat(pauta.getDescricao(), is("Pauta para aprovação de PLR"));
    }

    @Test
    public void deve_nao_buscar_por_id_registro_nao_encontrado_lanca_exception() {
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarPorId(Long.MAX_VALUE));
        assertThat(exception.getMessage(), is("Pauta 9223372036854775807 não encontrado"));
    }
}
