package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.domain.SituacaoSessao;
import br.com.prova.votacao.exception.RecursoNaoEncontradoException;
import br.com.prova.votacao.exception.ValidacaoException;
import br.com.prova.votacao.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.com.prova.votacao.domain.SituacaoSessao.ABERTA;
import static br.com.prova.votacao.domain.SituacaoSessao.NAO_INICIADA;
import static java.lang.String.format;
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

    public Sessao abrir(Sessao sessao) {
        Sessao sessaoDb = buscarPorId(sessao.getId());
        if(!SituacaoSessao.NAO_INICIADA.equals(sessaoDb.getSituacao())){
            throw new ValidacaoException("Não é permitido abrir uma sessão na situação " + sessaoDb.getSituacao());
        }
        sessaoDb.setDataAbertura(LocalDateTime.now());
        sessaoDb.setSituacao(ABERTA);
        return repository.save(sessaoDb);
    }

    public Sessao buscarPorId(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(format("Sessão %d não encontrado", id)));
    }

}
