package br.com.prova.votacao.controller;

import br.com.prova.votacao.controller.dto.PautaDTO;
import br.com.prova.votacao.controller.form.PautaForm;
import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.service.PautaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Locale;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<PautaDTO> cadastrar(@RequestBody @Valid PautaForm form, UriComponentsBuilder uriBuilder, Locale locale) {
        Pauta pauta = pautaService.incluir(form.toEntity());
        URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).body(new PautaDTO(pauta));
    }

}
