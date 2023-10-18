package br.com.gerenciadorusuarios.usuarioservico.resource.v1;

import br.com.gerenciadorusuarios.usuarioservico.resource.AbstractUsuarioResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "usuarios/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource extends AbstractUsuarioResource {
}
