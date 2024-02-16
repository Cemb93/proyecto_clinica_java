package cm.api.tools;

import cm.api.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
  String secret = "123456";
  String firmaToken = "cm";

  public String generarToken(Usuario usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
              .withIssuer(firmaToken)
              .withSubject(usuario.getUsername())
              .withClaim("id", usuario.getId())
              .withExpiresAt(generarFechaExpiracion())
              .sign(algorithm);
      return token;
    } catch (JWTCreationException exception){
      throw new RuntimeException();
    }
  }

  public String getSubject(String token) {
    System.out.println("BUSCANDO TOKEN: " + token);
    try {
      if (token == null) {
        throw new RuntimeException("No hay token");
      }
      Algorithm algorithm = Algorithm.HMAC256(secret);
      DecodedJWT verifier = JWT.require(algorithm)
              .withIssuer(firmaToken)
              .build().verify(token);
      return verifier.getSubject();
    } catch (JWTVerificationException exception){
      System.out.println("Error al verificar el token por: " + exception.toString());
      throw new RuntimeException("VERIFICADOR INVALIDO");
    }
  }

  private Instant generarFechaExpiracion() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
  }
}
