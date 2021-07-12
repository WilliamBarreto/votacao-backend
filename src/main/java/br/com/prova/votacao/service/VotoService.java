package br.com.prova.votacao.service;

import br.com.prova.votacao.client.ServicoExternoClient;
import br.com.prova.votacao.client.dto.CpfStatusDTO;
import br.com.prova.votacao.client.dto.StatusVoto;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.domain.SituacaoSessao;
import br.com.prova.votacao.domain.Voto;
import br.com.prova.votacao.exception.ValidacaoException;
import br.com.prova.votacao.repository.VotoCount;
import br.com.prova.votacao.repository.VotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

    private final SessaoService sessaoService;
    private final ServicoExternoClient servicoExternoClient;
    private final VotoRepository repository;

    public VotoService(SessaoService sessaoService, ServicoExternoClient servicoExternoClient, VotoRepository repository) {
        this.sessaoService = sessaoService;
        this.servicoExternoClient = servicoExternoClient;
        this.repository = repository;
    }

    public Voto incluir(Voto voto) {
        voto.setSessao(buscarSessao(voto));
        validar(voto);
        return repository.save(voto);
    }

    private Sessao buscarSessao(Voto voto) {
        return sessaoService.buscarPorId(voto.getSessao().getId());
    }

    private void validar(Voto voto) {
        validarSessaoAberta(voto);
        validarPeriodoVotacaoAberto(voto);
        validarCpfComVotoNaSessao(voto);
        validarCpfServicoExterno(voto);
    }

    private void validarSessaoAberta(Voto voto) {
        if(!SituacaoSessao.ABERTA.equals(voto.getSessao().getSituacao())) {
            throw new ValidacaoException("A sessão de votação não esta aberta para votos"); //403
        }
    }

    private void validarPeriodoVotacaoAberto(Voto voto) {
        if(sessaoService.isExpirada(voto.getSessao())) {
            throw new ValidacaoException("O período de votação foi encerrado");
        }
    }

    private void validarCpfServicoExterno(Voto voto) {
        CpfStatusDTO statusDTO = servicoExternoClient.consultaSituacaoCPF(voto.getCpfAssociado());
        if(StatusVoto.UNABLE_TO_VOTE.equals(statusDTO.getStatus())) {
            throw new ValidacaoException("Usuário não possui permissão para executar operação"); //403
        }
    }

    private void validarCpfComVotoNaSessao(Voto voto) {
        Optional<Voto> optionalVoto = repository.findByCpfAssociadoAndSessaoId(voto.getCpfAssociado(), voto.getSessao().getId());
        optionalVoto.ifPresent(v -> { throw new ValidacaoException("Cpf já possui voto cadastrado para a sessão"); });
    }

    public List<VotoCount> computar(Long idSessao) {
        return repository.countVotoByTotalVoto(idSessao);
    }
}
