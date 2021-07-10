package br.com.prova.votacao.controller;

import br.com.prova.votacao.controller.form.PautaForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deve_cadastrar_pauta() throws Exception {
        PautaForm pautaForm = PautaForm.builder().nome("Pauta").descricao("Descricao da pauta").build();
        String json = objectMapper.writeValueAsString(pautaForm);
        this.mockMvc.perform(post("/pautas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.nome", is(pautaForm.getNome())))
                .andExpect(jsonPath("$.descricao", is(pautaForm.getDescricao())));
    }


    @Test
    public void deve_nao_cadastrar_pauta_com_conteudo_vazio() throws Exception {
        this.mockMvc
                .perform(post("/pautas")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$.[0].propriedade", is("nome")))
                .andExpect(jsonPath("$.[0].messagem", is("must not be blank")));
    }

    @Test
    public void deve_nao_cadastrar_pauta_com_nome_nulo() throws Exception {
        PautaForm pautaForm = PautaForm.builder().descricao("Descricao da pauta").build();
        String json = objectMapper.writeValueAsString(pautaForm);
        this.mockMvc
                .perform(post("/pautas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade", is("nome")))
                .andExpect(jsonPath("$.[0].messagem", is("must not be blank")));
    }

    @Test
    public void deve_nao_cadastrar_pauta_com_nome_vazio() throws Exception {
        PautaForm pautaForm = PautaForm.builder().nome("").descricao("Descricao da pauta").build();
        String json = objectMapper.writeValueAsString(pautaForm);
        this.mockMvc
                .perform(post("/pautas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade", is("nome")))
                .andExpect(jsonPath("$.[0].messagem", is("must not be blank")));
    }

    @Test
    public void deve_nao_cadastrar_pauta_com_nome_de_tamanho_maior_que_200() throws Exception {
        String nome = String.format("%0201d", 0);
        PautaForm pautaForm = PautaForm.builder().nome(nome).descricao("Descricao da pauta").build();
        String json = objectMapper.writeValueAsString(pautaForm);
        this.mockMvc
                .perform(post("/pautas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade", is("nome")))
                .andExpect(jsonPath("$.[0].messagem", is("size must be between 0 and 200")));
    }

    @Test
    public void deve_nao_cadastrar_pauta_com_descricao_de_tamanho_maior_que_1000() throws Exception {
        String descricao = String.format("%01001d", 0);
        PautaForm pautaForm = PautaForm.builder().nome("Pauta").descricao(descricao).build();
        String json = objectMapper.writeValueAsString(pautaForm);
        this.mockMvc
                .perform(post("/pautas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade", is("descricao")))
                .andExpect(jsonPath("$.[0].messagem", is("size must be between 1 and 1000")));
    }

    @Test
    public void deve_nao_buscar_por_id_pauta_nao_encontrada() throws Exception {
        this.mockMvc
                .perform(get("/pautas/10000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messagem", is("Pauta 10000 não encontrado")));
    }

    @Test
    public void deve_buscar_por_id_pauta() throws Exception {
        this.mockMvc
                .perform(get("/pautas/1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Aprovação de PLR")))
                .andExpect(jsonPath("$.descricao", is("Pauta para aprovação de PLR")));
    }
}
