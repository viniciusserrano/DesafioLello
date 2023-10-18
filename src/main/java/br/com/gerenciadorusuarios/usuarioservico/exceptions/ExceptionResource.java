package br.com.gerenciadorusuarios.usuarioservico.exceptions;

import br.com.gerenciadorusuarios.usuarioservico.repository.dto.ErrorDto;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.exceptions.ValidatorErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionResource {

    @ExceptionHandler(ValidatorErrorException.class)
    public ResponseEntity<ErrorDto> handleValidatorErrorException(ValidatorErrorException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.valueOf(e.getStatus()));
    }
}
