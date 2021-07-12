package br.com.prova.votacao.producer;

import br.com.prova.votacao.domain.ResultadoSessao;
import br.com.prova.votacao.exception.ConversaoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
public class SessaoEncerradaProducer {

    private static final String TOPIC = "sessao-encerrada";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public SessaoEncerradaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(ResultadoSessao resultadoSessao) {
        ResultadoSessaoMessage message = new ResultadoSessaoMessage(resultadoSessao);
        String json = toJson(message);
        this.kafkaTemplate.send(TOPIC, json);
    }

    private String toJson(ResultadoSessaoMessage message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new ConversaoException("Erro ao converter mensagem para json");
        }
    }
}
