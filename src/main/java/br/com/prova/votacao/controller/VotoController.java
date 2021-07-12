package br.com.prova.votacao.controller;

import br.com.prova.votacao.controller.dto.VotoDTO;
import br.com.prova.votacao.controller.form.VotoForm;
import br.com.prova.votacao.domain.Voto;
import br.com.prova.votacao.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
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
@Api(tags = "Votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @ApiOperation(value = "Cadastra um Voto em uma Sessão aberta")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VotoDTO> cadastrar(@RequestBody @Valid VotoForm form, UriComponentsBuilder uriBuilder) {
        Voto voto = votoService.incluir(form.toEntity());
        URI uri = uriBuilder.path("/votos/{id}").buildAndExpand(voto.getId()).toUri();
        return ResponseEntity.created(uri).body(new VotoDTO(voto));
    }

}
