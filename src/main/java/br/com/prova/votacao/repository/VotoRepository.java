package br.com.prova.votacao.repository;

import br.com.prova.votacao.domain.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto,Long> {

    Optional<Voto> findByCpfAssociadoAndSessaoId(String cpfAssociado, Long idPauta);
}
