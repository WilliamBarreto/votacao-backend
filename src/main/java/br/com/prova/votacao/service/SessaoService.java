package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.domain.SituacaoSessao;
import br.com.prova.votacao.exception.RecursoNaoEncontradoException;
import br.com.prova.votacao.exception.ValidacaoException;
import br.com.prova.votacao.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.prova.votacao.domain.SituacaoSessao.*;
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
        validar(sessao);
        if(isNull(sessao.getDuracaoEmMinutos())) { sessao.setDuracaoEmMinutos(1); }
        sessao.setSituacao(NAO_INICIADA);
        return repository.save(sessao);
    }

    private void validar(Sessao sessao) {
        Optional<Sessao> optionalSessao = repository.findByPautaId(sessao.getPauta().getId());
        optionalSessao.ifPresent(s -> {
            throw new ValidacaoException(String.format("A pauta %s já foi vinculada a outra sessão", sessao.getPauta().getId()));
        });
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

    public Sessao fechar(Sessao sessao) {
        Sessao sessaoDb = buscarPorId(sessao.getId());
        if(!ABERTA.equals(sessaoDb.getSituacao())){
            throw new ValidacaoException("Não é permitido fechar uma sessão na situação " + sessaoDb.getSituacao());
        }
        sessaoDb.setDataFechamento(LocalDateTime.now());
        sessaoDb.setSituacao(FECHADA);
        return repository.save(sessaoDb);
    }

    public Sessao buscarPorId(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(format("Sessão %d não encontrado", id)));
    }

    public List<Sessao> buscarSessoesAbertas() {
        return repository.findBySituacao(ABERTA);
    }

    public boolean isExpirada(Sessao sessao) {
        LocalDateTime dataEncerramento = sessao.getDataAbertura().plusMinutes((long) sessao.getDuracaoEmMinutos());
        return dataEncerramento.isBefore(LocalDateTime.now());
    }
}
