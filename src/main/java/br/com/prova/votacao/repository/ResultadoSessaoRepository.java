package br.com.prova.votacao.repository;

import br.com.prova.votacao.domain.ResultadoSessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultadoSessaoRepository extends JpaRepository<ResultadoSessao, Long> {
}
