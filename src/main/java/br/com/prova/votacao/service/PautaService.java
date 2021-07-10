package br.com.prova.votacao.service;

import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.repository.PautaRepository;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    private final PautaRepository repository;

    public PautaService(PautaRepository pautaRepository) {
        this.repository = pautaRepository;
    }

    public Pauta incluir(Pauta pauta) {
        return repository.save(pauta);
    }
}
