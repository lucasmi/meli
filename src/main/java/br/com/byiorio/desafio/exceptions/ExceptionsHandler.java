package br.com.byiorio.desafio.exceptions;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.models.BasicErrorDTO;
import br.com.byiorio.desafio.models.ErrorDTO;

@ControllerAdvice
public class ExceptionsHandler {
    Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<BasicErrorDTO> defaultException(Exception ex, WebRequest request) {
        logger.error("Exception:", ex);

        ArrayList<ErrorDTO> errorsArr = new ArrayList<>();
        errorsArr.add(ErrorDTO.builder().codigo(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .msg(ex.getMessage()).build());

        return ResponseEntity.internalServerError().body(BasicErrorDTO.builder().errors(errorsArr).build());
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<BasicErrorDTO> argumentInvalido(MethodArgumentNotValidException e, WebRequest request) {

        logger.error("MethodArgumentNotValidException:", e);

        ArrayList<ErrorDTO> errorsArr = new ArrayList<>();

        for (FieldError fieldError : e.getFieldErrors()) {
            errorsArr.add(ErrorDTO.builder().codigo("400")
                    .msg(fieldError.getField() + ":" + fieldError.getDefaultMessage()).build());

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicErrorDTO.builder().errors(errorsArr).build());
    }

    @ExceptionHandler(value = { JpaJsonException.class })
    public ResponseEntity<BasicErrorDTO> defaultException(JpaJsonException ex, WebRequest request) {
        logger.error("Exception:", ex);

        ArrayList<ErrorDTO> errorsArr = new ArrayList<>();
        errorsArr.add(ErrorDTO.builder().codigo(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .msg(ex.getDescricao()).build());

        return ResponseEntity.badRequest().body(BasicErrorDTO.builder().errors(errorsArr).build());
    }
}
