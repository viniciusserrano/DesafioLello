package br.com.gerenciadorusuarios.usuarioservico.service.converter;

import br.com.gerenciadorusuarios.usuarioservico.repository.dto.UsuarioDto;
import br.com.gerenciadorusuarios.usuarioservico.repository.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioConverter {
    public UsuarioDto toDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDescricao(usuario.getDescricao());
        dto.setDataRegistro(usuario.getDataRegistro());
        return dto;
    }

    public Usuario toEntity(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setDescricao(dto.getDescricao());
        usuario.setDataRegistro(dto.getDataRegistro());
        return usuario;
    }

    public Page<UsuarioDto> toDtoPage(Page<Usuario> usuarioPage) {
        List<UsuarioDto> usuarioDtoList = usuarioPage.getContent()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(usuarioDtoList, usuarioPage.getPageable(), usuarioPage.getTotalElements());
    }
}
