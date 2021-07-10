package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.exception.RecursoNaoEncontradoException;
import br.com.prova.votacao.repository.PautaRepository;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class PautaService {

    private final PautaRepository repository;

    public PautaService(PautaRepository pautaRepository) {
        this.repository = pautaRepository;
    }

    public Pauta incluir(Pauta pauta) {
        return repository.save(pauta);
    }

    public Pauta buscarPorId(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(format("Pauta %d não encontrado", id)));
    }
}
