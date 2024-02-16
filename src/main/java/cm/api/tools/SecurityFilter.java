package cm.api.tools;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {
    var authHeader = request.getHeader("Authorization");
    System.out.println("FILTRANDO TOKEN: " + authHeader);
    if (authHeader != null) {
      // throw new RuntimeException("Token invalido");
      var token = authHeader.replace("Bearer ", "");
      System.out.println("TOKEN - 2: " + authHeader);
      System.out.println("TOKEN - 3: " + tokenService.getSubject(token));
      var subject = tokenService.getSubject(token);
      if (subject != null) {
        var usuario = usuarioRepository.findByUsername(subject);
        var authentication = new UsernamePasswordAuthenticationToken(
                usuario,
                null,
                usuario.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }

}
