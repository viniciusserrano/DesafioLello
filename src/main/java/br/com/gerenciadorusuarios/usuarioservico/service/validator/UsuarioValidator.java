package br.com.gerenciadorusuarios.usuarioservico.service.validator;

import br.com.gerenciadorusuarios.usuarioservico.repository.dto.UsuarioDto;
import br.com.gerenciadorusuarios.usuarioservico.repository.entity.Usuario;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.exceptions.ValidatorErrorException;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UsuarioValidator {

    private static final String DESCRICAO_NAO_ALTERADA = "A descrição não pode ser alterada";
    private static final String EMAIL_OBRIGATORIO = "Email é obrigatório";
    private static final String EMAIL_INVALIDO = "Email inválido";
    private static final String NOME_OBRIGATORIO = "Nome é obrigatório";
    private static final String NOME_LIMITE_CARACTERES = "Nome ultrapassou o limite de caracteres";
    private static final String USUARIO_NAO_PODE_SER_NULO = "Usuário não pode ser nulo";
    private static final String DESCRICAO_NAO_PODE_SER_VAZIA = "A Descrição não pode ser vazia";
    private static final String DESCRICAO_MUITO_EXTENSA = "A Descrição está muito extensa";

    public void validarUsuario(UsuarioDto usuarioDto) throws ValidatorErrorException {
        if (usuarioDto == null) {
            throw new ValidatorErrorException(USUARIO_NAO_PODE_SER_NULO, HttpStatus.BAD_REQUEST.value());
        }

        validarEmail(usuarioDto);
        validarNome(usuarioDto);
        validarDescricao(usuarioDto);
    }

    public void validarEmail(UsuarioDto usuarioDto) throws ValidatorErrorException {
        var email = usuarioDto.getEmail();

        if (email == null) {
            throw new ValidatorErrorException(EMAIL_OBRIGATORIO, HttpStatus.BAD_REQUEST.value());
        }
        if (StringUtils.isEmpty(email)) {
            throw new ValidatorErrorException(EMAIL_OBRIGATORIO, HttpStatus.BAD_REQUEST.value());
        }

        String emailRegex = "^(?=.{1,256}$)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new ValidatorErrorException(EMAIL_INVALIDO, HttpStatus.BAD_REQUEST.value());
        }
    }

    public void validarNome(UsuarioDto usuarioDto) throws ValidatorErrorException {
        var nome = usuarioDto.getNome();

        if (nome == null) {
            throw new ValidatorErrorException(NOME_OBRIGATORIO, HttpStatus.BAD_REQUEST.value());
        }
        if (StringUtils.isEmpty(nome)) {
            throw new ValidatorErrorException(NOME_OBRIGATORIO, HttpStatus.BAD_REQUEST.value());
        }
        if (nome.length() > 255) {
            throw new ValidatorErrorException(NOME_LIMITE_CARACTERES, HttpStatus.BAD_REQUEST.value());
        }
    }

    private void validarDescricao(UsuarioDto usuarioDto) {
        var descricao = usuarioDto.getDescricao();

        if (descricao.isEmpty()) {
            throw new IllegalArgumentException(DESCRICAO_NAO_PODE_SER_VAZIA);
        }
        if (descricao.length() > 400) {
            throw new IllegalArgumentException(DESCRICAO_MUITO_EXTENSA);
        }
    }

    public void alterarDescricao(UsuarioDto usuarioDto, Usuario usuarioExistente) throws ValidatorErrorException {
        var descricaoDto = usuarioDto.getDescricao();
        var descricaoExistente = usuarioExistente.getDescricao();

        if (descricaoDto == null || descricaoDto.isEmpty()) {
            // Se a descrição no DTO for nula ou vazia, defina-a como a descrição existente.
            usuarioDto.setDescricao(descricaoExistente);
        } else if (!descricaoDto.equals(descricaoExistente)) {
            throw new ValidatorErrorException(DESCRICAO_NAO_ALTERADA, HttpStatus.BAD_REQUEST.value());
        }
    }
}