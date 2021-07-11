package br.com.prova.votacao.controller;

import br.com.prova.votacao.controller.dto.SessaoDTO;
import br.com.prova.votacao.controller.form.SessaoForm;
import br.com.prova.votacao.domain.Sessao;
import br.com.prova.votacao.service.SessaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    public ResponseEntity<SessaoDTO> cadastrar(@RequestBody @Valid SessaoForm form, UriComponentsBuilder uriBuilder) {
        Sessao sessao = sessaoService.incluir(form.toEntity());
        URI uri = uriBuilder.path("/sessoes/{id}").buildAndExpand(sessao.getId()).toUri();
        return ResponseEntity.created(uri).body(new SessaoDTO(sessao));
    }


}
