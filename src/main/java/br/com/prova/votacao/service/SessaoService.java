package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.exception.RecursoNaoEncontradoException;
import br.com.prova.votacao.exception.ValidacaoException;
import br.com.prova.votacao.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import static br.com.prova.votacao.domain.SituacaoSessao.NAO_INICIADA;
import static java.util.Objects.isNull;

@Service
public class SessaoService {

    private final SessaoRepository repository;
    private final PautaService pautaService;

    public SessaoService(SessaoRepository repository, PautaService pautaService) {
        this.repository = repository;
        this.pautaService = pautaService;
    }

    public Sessao incluir(Sessao sessao) {
        sessao.setPauta(buscarPauta(sessao.getPauta()));
        if(isNull(sessao.getDuracaoEmMinutos())) { sessao.setDuracaoEmMinutos(1); }
        sessao.setSituacao(NAO_INICIADA);
        return repository.save(sessao);
    }

    private Pauta buscarPauta(Pauta pauta) {
        try {
            return pautaService.buscarPorId(pauta.getId());
        } catch (RecursoNaoEncontradoException e) {
            throw new ValidacaoException(e.getMessage());
        }
    }

}
