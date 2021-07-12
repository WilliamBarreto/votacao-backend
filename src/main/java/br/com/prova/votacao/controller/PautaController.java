package br.com.prova.votacao.controller;

import br.com.prova.votacao.controller.dto.PautaDTO;
import br.com.prova.votacao.controller.form.PautaForm;
import br.com.prova.votacao.domain.Pauta;
import br.com.prova.votacao.service.PautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pautas")
@Api(tags = "Pautas")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @ApiOperation(value = "Cadastra uma nova Pauta")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PautaDTO> cadastrar(@RequestBody @Valid PautaForm form, UriComponentsBuilder uriBuilder) {
        Pauta pauta = pautaService.incluir(form.toEntity());
        URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).body(new PautaDTO(pauta));
    }

    @ApiOperation(value = "Busca uma Pauta por id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PautaDTO buscarPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPorId(id);
        return new PautaDTO(pauta);
    }

}
