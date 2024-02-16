package cm.api.tools;

import org.springframework.data.jpa.repository.JpaRepository;
import cm.api.models.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  UserDetails findByUsername(String username);
  //Usuario findByUsername(String username);
}
