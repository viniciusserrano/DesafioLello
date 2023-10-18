package br.com.gerenciadorusuarios.usuarioservico.service;

import br.com.gerenciadorusuarios.usuarioservico.repository.dao.UsuarioDao;
import br.com.gerenciadorusuarios.usuarioservico.repository.dto.UsuarioDto;
import br.com.gerenciadorusuarios.usuarioservico.repository.entity.Usuario;
import br.com.gerenciadorusuarios.usuarioservico.service.converter.UsuarioConverter;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.UsuarioValidator;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.exceptions.ValidatorErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @Autowired
    private UsuarioValidator usuarioValidator;

    public UsuarioDto registrarUsuario(UsuarioDto usuarioDto) throws ValidatorErrorException {
        Usuario usuario = usuarioConverter.toEntity(usuarioDto);
        usuario.setDataRegistro(Instant.now());
        usuarioValidator.validarUsuario(usuarioDto);
        Usuario usuarioSalvo = usuarioDao.save(usuario);

        return usuarioConverter.toDto(usuarioSalvo);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public Page<UsuarioDto> buscarTodosUsuariosPaginados(Pageable page) {
        Page<Usuario> usuarioPage = usuarioDao.findAll(page);
        return usuarioConverter.toDtoPage(usuarioPage);
    }

    public UsuarioDto alterarUsuario(Long id, UsuarioDto usuarioDto) throws ValidatorErrorException {
        Usuario usuarioExistente = usuarioDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuarioValidator.validarNome(usuarioDto);
        usuarioValidator.validarEmail(usuarioDto);
        usuarioValidator.alterarDescricao(usuarioDto, usuarioExistente);
        usuarioExistente.setNome(usuarioDto.getNome());
        usuarioExistente.setEmail(usuarioDto.getEmail());
        usuarioExistente.setDataRegistro(Instant.now());

        Usuario usuarioAtualizado = usuarioDao.save(usuarioExistente);

        return usuarioConverter.toDto(usuarioAtualizado);
    }
}
