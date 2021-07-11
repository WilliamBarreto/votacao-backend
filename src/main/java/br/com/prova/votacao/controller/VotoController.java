package br.com.prova.votacao.controller;

import br.com.prova.votacao.controller.dto.VotoDTO;
import br.com.prova.votacao.controller.form.VotoForm;
import br.com.prova.votacao.domain.Voto;
import br.com.prova.votacao.service.VotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public ResponseEntity<VotoDTO> cadastrar(@RequestBody @Valid VotoForm form, UriComponentsBuilder uriBuilder) {
        Voto voto = votoService.incluir(form.toEntity());
        URI uri = uriBuilder.path("/votos/{id}").buildAndExpand(voto.getId()).toUri();
        return ResponseEntity.created(uri).body(new VotoDTO(voto));
    }

}
