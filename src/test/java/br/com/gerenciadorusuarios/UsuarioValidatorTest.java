package br.com.gerenciadorusuarios;

import br.com.gerenciadorusuarios.usuarioservico.repository.dto.UsuarioDto;
import br.com.gerenciadorusuarios.usuarioservico.repository.entity.Usuario;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.UsuarioValidator;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.exceptions.ValidatorErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsuarioValidatorTest {
    private UsuarioValidator usuarioValidator;

    @BeforeEach
    public void setUp() {
        usuarioValidator = new UsuarioValidator();
    }

    @Test
    public void testValidarUsuarioDtoNuloDeveLancarExcecao() {
        UsuarioDto usuarioDto = null;

        ValidatorErrorException exception = assertThrows(ValidatorErrorException.class,
                () -> usuarioValidator.validarUsuario(usuarioDto));
        assertEquals("Usuário não pode ser nulo", exception.getMessage());
    }

    @Test
    public void testValidarEmailEmailNuloDeveLancarExcecao() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail(null);

        ValidatorErrorException exception = assertThrows(ValidatorErrorException.class,
                () -> usuarioValidator.validarEmail(usuarioDto));
        assertEquals("Email é obrigatório", exception.getMessage());
    }

    @Test
    public void testValidarEmailVazioDeveLancarExcecao() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail("");

        ValidatorErrorException exception = assertThrows(ValidatorErrorException.class,
                () -> usuarioValidator.validarEmail(usuarioDto));
        assertEquals("Email é obrigatório", exception.getMessage());
    }

    @Test
    public void testValidarEmailInvalidoDeveLancarExcecao() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail("email.invalido");

        ValidatorErrorException exception = assertThrows(ValidatorErrorException.class,
                () -> usuarioValidator.validarEmail(usuarioDto));
        assertEquals("Email inválido", exception.getMessage());
    }

    @Test
    public void testValidarNomeNuloDeveLancarExcecao() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome(null);

        ValidatorErrorException exception = assertThrows(ValidatorErrorException.class,
                () -> usuarioValidator.validarNome(usuarioDto));
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void testValidarNomeNomeVazioDeveLancarExcecao() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome("");

        ValidatorErrorException exception = assertThrows(ValidatorErrorException.class,
                () -> usuarioValidator.validarNome(usuarioDto));
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    public void testValidarNomeNomeComLimiteExcedidoDeveLancarExcecao() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome("Nome muito longo acima do limite permitido".repeat(10));

        ValidatorErrorException exception = assertThrows(ValidatorErrorException.class,
                () -> usuarioValidator.validarNome(usuarioDto));
        assertEquals("Nome ultrapassou o limite de caracteres", exception.getMessage());
    }

    @Test
    public void testAlterarDescricaoNula() throws ValidatorErrorException {
        UsuarioDto usuarioDto = new UsuarioDto();
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setDescricao("Descrição Existente");

        usuarioValidator.alterarDescricao(usuarioDto, usuarioExistente);

        assertEquals(usuarioExistente.getDescricao(), usuarioDto.getDescricao());
    }

    @Test
    public void testAlterarDescricaoDiferente() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setDescricao("Nova Descrição");
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setDescricao("Descrição Existente");

        assertThrows(ValidatorErrorException.class, () -> usuarioValidator.alterarDescricao(usuarioDto, usuarioExistente));
    }
}
