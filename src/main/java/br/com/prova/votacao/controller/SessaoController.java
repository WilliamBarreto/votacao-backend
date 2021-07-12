package br.com.prova.votacao.controller;

import br.com.prova.votacao.controller.dto.SessaoDTO;
import br.com.prova.votacao.controller.form.SessaoForm;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.service.SessaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/sessoes")
@Api(tags = "Sessões")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }


    @ApiOperation(value = "Cadastra uma nova Sessão")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessaoDTO> cadastrar(@RequestBody @Valid SessaoForm form, UriComponentsBuilder uriBuilder) {
        Sessao sessao = sessaoService.incluir(form.toEntity());
        URI uri = uriBuilder.path("/sessoes/{id}").buildAndExpand(sessao.getId()).toUri();
        return ResponseEntity.created(uri).body(new SessaoDTO(sessao));
    }

    @ApiOperation(value = "Abre uma Sessão cadastrada para votação")
    @PutMapping(value = "/{id}/abrir", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessaoDTO> abrir(@PathVariable Long id) {
        Sessao sessao = sessaoService.abrir(Sessao.builder().id(id).build());
        return ResponseEntity.ok(new SessaoDTO(sessao));
    }

}
