package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.Pauta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
