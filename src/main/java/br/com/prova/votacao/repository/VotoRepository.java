package br.com.prova.votacao.repository;

import br.com.prova.votacao.domain.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto,Long> {

    Optional<Voto> findByCpfAssociadoAndSessaoId(String cpfAssociado, Long idPauta);

    @Query("SELECT " +
            "new br.com.prova.votacao.repository.VotoCount(v.voto, COUNT(v.voto)) " +
            "FROM Voto AS v " +
            "WHERE v.sessao.id = :idSessao " +
            "GROUP BY v.voto ORDER BY v.voto DESC")
    List<VotoCount> countVotoByTotalVoto(Long idSessao);


}
