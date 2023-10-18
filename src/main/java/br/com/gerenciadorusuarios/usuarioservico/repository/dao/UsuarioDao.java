package br.com.gerenciadorusuarios.usuarioservico.repository.dao;

import br.com.gerenciadorusuarios.usuarioservico.repository.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Long> {
}
