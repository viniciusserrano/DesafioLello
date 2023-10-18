package br.com.gerenciadorusuarios;

import br.com.gerenciadorusuarios.usuarioservico.repository.dao.UsuarioDao;
import br.com.gerenciadorusuarios.usuarioservico.repository.dto.UsuarioDto;
import br.com.gerenciadorusuarios.usuarioservico.repository.entity.Usuario;
import br.com.gerenciadorusuarios.usuarioservico.service.UsuarioService;
import br.com.gerenciadorusuarios.usuarioservico.service.converter.UsuarioConverter;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.UsuarioValidator;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.exceptions.ValidatorErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioDao usuarioDao;

    @Mock
    private UsuarioConverter usuarioConverter;

    @Mock
    private UsuarioValidator usuarioValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarUsuario() throws ValidatorErrorException {
        UsuarioDto usuarioDto = new UsuarioDto();
        Usuario usuario = new Usuario();
        usuario.setDataRegistro(Instant.now());

        when(usuarioConverter.toEntity(usuarioDto)).thenReturn(usuario);
        when(usuarioDao.save(usuario)).thenReturn(usuario);
        when(usuarioConverter.toDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto result = usuarioService.registrarUsuario(usuarioDto);

        verify(usuarioValidator).validarUsuario(usuarioDto);
        verify(usuarioConverter).toEntity(usuarioDto);
        verify(usuarioDao).save(usuario);
        verify(usuarioConverter).toDto(usuario);
        assertEquals(usuarioDto, result);
    }

    @Test
    public void testBuscarUsuarioPorId() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioDao.findById(id)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.buscarUsuarioPorId(id);

        verify(usuarioDao).findById(id);
        assertEquals(usuario, result);
    }

    @Test
    public void testBuscarTodosUsuariosPaginados() {
        Pageable pageable = mock(Pageable.class);
        Page<Usuario> usuarioPage = mock(Page.class);

        when(usuarioDao.findAll(pageable)).thenReturn(usuarioPage);
        when(usuarioConverter.toDtoPage(usuarioPage)).thenReturn(mock(Page.class));

        Page<UsuarioDto> result = usuarioService.buscarTodosUsuariosPaginados(pageable);

        verify(usuarioDao).findAll(pageable);
        verify(usuarioConverter).toDtoPage(usuarioPage);
    }

    @Test
    public void testAlterarUsuario() throws ValidatorErrorException {
        Long id = 1L;
        UsuarioDto usuarioDto = new UsuarioDto();
        Usuario usuarioExistente = new Usuario();
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(id);

        when(usuarioDao.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioDao.save(usuarioExistente)).thenReturn(usuarioAtualizado);
        when(usuarioConverter.toDto(usuarioAtualizado)).thenReturn(usuarioDto);

        UsuarioDto result = usuarioService.alterarUsuario(id, usuarioDto);

        verify(usuarioDao).findById(id);
        verify(usuarioDao).save(usuarioExistente);
        verify(usuarioConverter).toDto(usuarioAtualizado);
        verify(usuarioValidator).validarNome(usuarioDto);
        verify(usuarioValidator).validarEmail(usuarioDto);
        verify(usuarioValidator).alterarDescricao(usuarioDto, usuarioExistente);
        assertEquals(usuarioDto, result);
    }
}
