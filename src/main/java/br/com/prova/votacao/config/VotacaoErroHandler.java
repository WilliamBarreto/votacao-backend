package br.com.prova.votacao.config;

import br.com.prova.votacao.controller.dto.ErroDTO;
import br.com.prova.votacao.controller.dto.PropriedadeErroDTO;
import br.com.prova.votacao.exception.RecursoNaoEncontradoException;
import br.com.prova.votacao.exception.ValidacaoException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class VotacaoErroHandler {

    private final MessageSource messageSource;

    public VotacaoErroHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDTO> handleErroArgumentos(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        return fieldErrors.stream()
                .map(this::toPropriedadeErroDTO)
                .sorted(Comparator.comparing(PropriedadeErroDTO::getPropriedade))
                .collect(Collectors.toList());
    }

    private PropriedadeErroDTO toPropriedadeErroDTO(FieldError fieldError) {
        String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
        return new PropriedadeErroDTO(fieldError.getField(), message);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ErroDTO handleEntidadeNaoEncontrado(RecursoNaoEncontradoException exception) {
        return new ErroDTO(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidacaoException.class)
    public ErroDTO handleValidacao(ValidacaoException exception){
        return new ErroDTO(exception.getMessage());
    }

}
