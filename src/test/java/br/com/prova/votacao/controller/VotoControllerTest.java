package br.com.prova.votacao.controller;

import br.com.prova.votacao.IntegracaoTests;
import br.com.prova.votacao.client.ServicoExternoClient;
import br.com.prova.votacao.client.dto.CpfStatusDTO;
import br.com.prova.votacao.client.dto.StatusVoto;
import br.com.prova.votacao.controller.form.VotoForm;
import br.com.prova.votacao.domain.OpcaoVoto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VotoControllerTest extends IntegracaoTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServicoExternoClient client;


    @Test
    public void deve_cadastrar_voto() throws Exception {
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.ABLE_TO_VOTE));
        VotoForm votoForm = VotoForm.builder().idSessao(3L).cpf("00000000000").voto(OpcaoVoto.SIM).build();
        String json = objectMapper.writeValueAsString(votoForm);
        this.mockMvc.perform(post("/votos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",       is(notNullValue())))
                .andExpect(jsonPath("$.idSessao", is(3)))
                .andExpect(jsonPath("$.cpf",      is("00000000000")))
                .andExpect(jsonPath("$.voto",     is("SIM")));
    }

    @Test
    public void deve_nao_cadastrar_voto_sem_campos_obrigatorios() throws Exception {
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.ABLE_TO_VOTE));
        String json = "{ }";
        this.mockMvc.perform(post("/votos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].propriedade",   is("cpf")))
                .andExpect(jsonPath("$.[0].messagem",      is("must not be null")))
                .andExpect(jsonPath("$.[1].propriedade",   is("idSessao")))
                .andExpect(jsonPath("$.[1].messagem",      is("must not be null")))
                .andExpect(jsonPath("$.[2].propriedade",   is("voto")))
                .andExpect(jsonPath("$.[2].messagem",      is("must not be null")));

    }

    @Test
    public void deve_nao_cadastrar_voto_sessao_nao_iniciada() throws Exception {
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.ABLE_TO_VOTE));
        VotoForm votoForm = VotoForm.builder().idSessao(1L).cpf("00000000000").voto(OpcaoVoto.SIM).build();
        String json = objectMapper.writeValueAsString(votoForm);
        this.mockMvc.perform(post("/votos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messagem",       is("A sessão de votação não esta aberta para votos")));
    }


    @Test
    public void deve_nao_cadastrar_voto_sessao_fechada() throws Exception {
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.ABLE_TO_VOTE));
        VotoForm votoForm = VotoForm.builder().idSessao(2L).cpf("00000000000").voto(OpcaoVoto.SIM).build();
        String json = objectMapper.writeValueAsString(votoForm);
        this.mockMvc.perform(post("/votos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messagem",       is("A sessão de votação não esta aberta para votos")));
    }

    @Test
    public void deve_nao_cadastrar_voto_quando_servico_externo_retorna_unable_to_vote() throws Exception {
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.UNABLE_TO_VOTE));
        VotoForm votoForm = VotoForm.builder().idSessao(3L).cpf("00000000000").voto(OpcaoVoto.SIM).build();
        String json = objectMapper.writeValueAsString(votoForm);
        this.mockMvc.perform(post("/votos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messagem",       is("Usuário não possui permissão para executar operação")));
    }

    @Test
    public void deve_nao_cadastrar_voto_sessao_nao_existente() throws Exception {
        when(client.consultaSituacaoCPF("00000000000")).thenReturn(new CpfStatusDTO(StatusVoto.ABLE_TO_VOTE));
        VotoForm votoForm = VotoForm.builder().idSessao(Long.MAX_VALUE).cpf("00000000000").voto(OpcaoVoto.SIM).build();
        String json = objectMapper.writeValueAsString(votoForm);
        this.mockMvc.perform(post("/votos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messagem",       is("Sessão 9223372036854775807 não encontrado")));
    }

}
