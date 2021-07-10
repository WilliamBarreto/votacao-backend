package br.com.prova.votacao.controller;

import br.com.prova.votacao.controller.dto.PautaDTO;
import br.com.prova.votacao.controller.form.PautaForm;
import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.service.PautaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<PautaDTO> cadastrar(@RequestBody @Valid PautaForm form, UriComponentsBuilder uriBuilder) {
        Pauta pauta = pautaService.incluir(form.toEntity());
        URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).body(new PautaDTO(pauta));
    }

    @GetMapping("/{id}")
    public PautaDTO buscarPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPorId(id);
        return new PautaDTO(pauta);
    }

}
