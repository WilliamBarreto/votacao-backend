package br.com.prova.votacao.client;

import br.com.prova.votacao.client.dto.CpfStatusDTO;
import br.com.prova.votacao.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Service
public class ServicoExternoClient {

    private final RestTemplate restTemplate;

    public ServicoExternoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CpfStatusDTO consultaSituacaoCPF(String cpf){
       try {
           return restTemplate.getForObject("https://user-info.herokuapp.com/users/"+ cpf, CpfStatusDTO.class);
       } catch (HttpClientErrorException.NotFound e) {
           throw new RecursoNaoEncontradoException(format("CPF %s inválido.",cpf));
       }
    }
}
