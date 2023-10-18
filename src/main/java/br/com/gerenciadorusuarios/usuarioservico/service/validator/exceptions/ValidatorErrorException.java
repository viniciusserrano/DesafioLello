package br.com.gerenciadorusuarios.usuarioservico.service.validator.exceptions;

public class ValidatorErrorException extends Exception {
    private final int status;

    public ValidatorErrorException(final String message, Integer status) {
        super(message.toString());
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
