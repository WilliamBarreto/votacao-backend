package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.ResultadoSessao;
import br.com.prova.votacao.repository.ResultadoSessaoRepository;
import org.springframework.stereotype.Service;

@Service
public class ResultadoSessaoService {

    private final ResultadoSessaoRepository repository;

    public ResultadoSessaoService(ResultadoSessaoRepository repository) {
        this.repository = repository;
    }

    public ResultadoSessao incluir(ResultadoSessao resultadoSessao) {
        return repository.save(resultadoSessao);
    }
}
