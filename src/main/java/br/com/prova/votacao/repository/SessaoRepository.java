package br.com.prova.votacao.repository;

import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.domain.SituacaoSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    Optional<Sessao> findByPautaId(Long idPauta);
    List<Sessao> findBySituacao(SituacaoSessao situacao);
}
