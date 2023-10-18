package br.com.gerenciadorusuarios.usuarioservico.resource;

import br.com.gerenciadorusuarios.usuarioservico.repository.dto.UsuarioDto;
import br.com.gerenciadorusuarios.usuarioservico.repository.entity.Usuario;
import br.com.gerenciadorusuarios.usuarioservico.service.UsuarioService;
import br.com.gerenciadorusuarios.usuarioservico.service.validator.exceptions.ValidatorErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public abstract class AbstractUsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> registrarUsuario(@RequestBody UsuarioDto usuarioDto) throws ValidatorErrorException {
        UsuarioDto novoUsuario = usuarioService.registrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/pageable")
    public ResponseEntity<List<UsuarioDto>> listarTodosUsuarios(@PageableDefault(size = 5) Pageable page) {
        Page<UsuarioDto> usuarios = usuarioService.buscarTodosUsuariosPaginados(page);
        return ResponseEntity.ok(usuarios.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> alterarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) throws ValidatorErrorException {
        UsuarioDto usuarioAtualizado = usuarioService.alterarUsuario(id, usuarioDto);
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok(usuarioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
