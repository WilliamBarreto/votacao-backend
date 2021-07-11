package br.com.prova.votacao.controller;

import br.com.prova.votacao.IntegracaoTests;
import br.com.prova.votacao.controller.form.SessaoForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SessaoControllerTest extends IntegracaoTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deve_nao_cadastrar_sessao_campos_obrigatorios() throws Exception {
        SessaoForm sessaoForm = SessaoForm.builder().build();
        String json = objectMapper.writeValueAsString(sessaoForm);
        this.mockMvc.perform(post("/sessoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade", is("idPauta")))
                .andExpect(jsonPath("$.[0].messagem", is("must not be null")));
    }

    @Test
    public void deve_nao_cadastrar_sessao_sem_pauta() throws Exception {
        SessaoForm sessaoForm = SessaoForm.builder().duracaoEmMinutos(2).build();
        String json = objectMapper.writeValueAsString(sessaoForm);
        this.mockMvc.perform(post("/sessoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade", is("idPauta")))
                .andExpect(jsonPath("$.[0].messagem", is("must not be null")));
    }

    @Test
    public void deve_nao_cadastrar_sessao_com_duracao_em_minutos_zero() throws Exception {
        SessaoForm sessaoForm = SessaoForm.builder().idPauta(1L).duracaoEmMinutos(0).build();
        String json = objectMapper.writeValueAsString(sessaoForm);
        this.mockMvc.perform(post("/sessoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade", is("duracaoEmMinutos")))
                .andExpect(jsonPath("$.[0].messagem", is("must be greater than 0")));
    }

    @Test
    public void deve_nao_cadastrar_sessao_com_duracao_em_minutos_negativo() throws Exception {
        SessaoForm sessaoForm = SessaoForm.builder().idPauta(1L).duracaoEmMinutos(-1).build();
        String json = objectMapper.writeValueAsString(sessaoForm);
        this.mockMvc.perform(post("/sessoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade", is("duracaoEmMinutos")))
                .andExpect(jsonPath("$.[0].messagem", is("must be greater than 0")));
    }

    @Test
    public void deve_nao_cadastrar_sessao_com_pauta_nao_encontrada() throws Exception {
        SessaoForm sessaoForm = SessaoForm.builder().idPauta(Long.MAX_VALUE).build();
        String json = objectMapper.writeValueAsString(sessaoForm);
        this.mockMvc.perform(post("/sessoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messagem", is("Pauta 9223372036854775807 não encontrado")));
    }

    @Test
    public void deve_cadastrar_sessao() throws Exception {
        SessaoForm sessaoForm = SessaoForm.builder().idPauta(1L).duracaoEmMinutos(2).build();
        String json = objectMapper.writeValueAsString(sessaoForm);
        this.mockMvc.perform(post("/sessoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.idPauta", is(1)))
                .andExpect(jsonPath("$.duracaoEmMinutos", is(2)))
                .andExpect(jsonPath("$.situacao", is("NAO_INICIADA")));
    }

    @Test
    public void deve_cadastrar_sessao_sem_duracao_em_minutos() throws Exception {
        SessaoForm sessaoForm = SessaoForm.builder().idPauta(1L).build();
        String json = objectMapper.writeValueAsString(sessaoForm);
        this.mockMvc.perform(post("/sessoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.idPauta", is(1)))
                .andExpect(jsonPath("$.duracaoEmMinutos", is(1)))
                .andExpect(jsonPath("$.situacao", is("NAO_INICIADA")));
    }


    @Test
    public void deve_nao_abrir_sessao_nao_encontrada() throws Exception {
        this.mockMvc.perform(put("/sessoes/"+Long.MAX_VALUE+"/abrir"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messagem", is("Sessão 9223372036854775807 não encontrado")));
    }

    @Test
    public void deve_nao_abrir_sessao_fechada() throws Exception {
        this.mockMvc.perform(put("/sessoes/2/abrir"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messagem", is("Não é permitido abrir uma sessão na situação FECHADA")));
    }

    @Test
    public void deve_abrir_sessao() throws Exception {
        this.mockMvc.perform(put("/sessoes/1/abrir"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.idPauta", is(2)))
                .andExpect(jsonPath("$.duracaoEmMinutos", is(1)))
                .andExpect(jsonPath("$.situacao", is("ABERTA")));
    }
}
