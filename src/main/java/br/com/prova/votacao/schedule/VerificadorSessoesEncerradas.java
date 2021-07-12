package br.com.prova.votacao.schedule;

import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.service.GeradorResultadoSessaoService;
import br.com.prova.votacao.service.SessaoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerificadorSessoesEncerradas {

    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;

    private final SessaoService sessaoService;
    private final GeradorResultadoSessaoService geradorResultadoSessao;

    public VerificadorSessoesEncerradas(SessaoService sessaoService, GeradorResultadoSessaoService geradorResultadoSessao) {
        this.sessaoService = sessaoService;
        this.geradorResultadoSessao = geradorResultadoSessao;
    }

    @Scheduled(fixedDelay = MINUTO)
    public void encerrarSessoes() {
        List<Sessao> sessoes = sessaoService.buscarSessoesAbertas();
        sessoes.forEach(sessao -> {
            if(sessaoService.isExpirada(sessao)) { geradorResultadoSessao.gerar(sessao); }
        });
    }
}
