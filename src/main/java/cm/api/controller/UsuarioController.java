package cm.api.controller;

import cm.api.models.Usuario;
import cm.api.record.DatosAutenticarUsuario;
import cm.api.record.DatosJwtToken;
import cm.api.record.DatosRegistrarUsuario;
import cm.api.record.DatosRespuestaUsuario;
import cm.api.tools.TokenService;
import cm.api.tools.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired // REALIZA INYECCION AUTOMATICA
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @PostMapping("/register")
  public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(
          @RequestBody DatosRegistrarUsuario body,
          UriComponentsBuilder uriComponentsBuilder
  ) {
    Usuario usuario = usuarioRepository.save(new Usuario(body));
    DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(
            usuario.getId(),
            usuario.getUsername(),
            usuario.getEmail(),
            usuario.getPassword()
    );
    URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
    return ResponseEntity.created(url).body(datosRespuestaUsuario);
  }

  @PostMapping("/login")
  public ResponseEntity autenticarUsuario(@RequestBody DatosAutenticarUsuario datosAutenticarUsuario) {
    Authentication authToken = new UsernamePasswordAuthenticationToken(
            datosAutenticarUsuario.username(),
            datosAutenticarUsuario.password()
    );
    var usuarioAutenticado = authenticationManager.authenticate(authToken);
    var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
    return ResponseEntity.ok(new DatosJwtToken(jwtToken));
  }
}
