package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.OpcaoVoto;
import br.com.prova.votacao.domain.ResultadoSessao;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.producer.SessaoEncerradaProducer;
import br.com.prova.votacao.repository.VotoCount;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.prova.votacao.domain.OpcaoVoto.NAO;
import static br.com.prova.votacao.domain.OpcaoVoto.SIM;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Service
public class GeradorResultadoSessaoService {

    private final SessaoService sessaoService;
    private final VotoService votoService;
    private final ResultadoSessaoService resultadoSessaoService;
    private final SessaoEncerradaProducer sessaoEncerradaProducer;

    public GeradorResultadoSessaoService(SessaoService sessaoService,
                                         VotoService votoService,
                                         ResultadoSessaoService resultadoSessaoService, SessaoEncerradaProducer sessaoEncerradaProducer) {
        this.sessaoService = sessaoService;
        this.votoService = votoService;
        this.resultadoSessaoService = resultadoSessaoService;
        this.sessaoEncerradaProducer = sessaoEncerradaProducer;
    }

    @Transactional(value = REQUIRES_NEW)
    public ResultadoSessao gerar(Sessao sessao) {
        Sessao sessaoFechada = sessaoService.fechar(sessao);
        ResultadoSessao resultadoSessao = salvarResultadoSessao(sessaoFechada);
        sessaoEncerradaProducer.sendMessage(resultadoSessao);
        return resultadoSessao;
    }

    private ResultadoSessao salvarResultadoSessao(Sessao sessao) {
        Map<OpcaoVoto, Integer> votos = computarVotos(sessao);
        ResultadoSessao resultadoSessao = ResultadoSessao.builder().sessao(sessao)
                .quantidadeVotosSim(votos.get(SIM))
                .quantidadeVotosNao(votos.get(NAO))
                .build();
        return resultadoSessaoService.incluir(resultadoSessao);
    }

    private Map<OpcaoVoto, Integer> computarVotos(Sessao sessao) {
        Map<OpcaoVoto, Integer> map = new HashMap<>();
        for (OpcaoVoto opcaoVoto : OpcaoVoto.values()) { map.put(opcaoVoto, 0); }
        List<VotoCount> votosComputados = votoService.computar(sessao.getId());
        votosComputados.forEach(votoComputado -> map.put(votoComputado.getVoto(), (int) votoComputado.getTotal()));
        return map;
    }


}
