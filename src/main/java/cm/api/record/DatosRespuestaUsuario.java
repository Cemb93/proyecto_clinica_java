package cm.api.record;

public record DatosRespuestaUsuario(
        Long id,
        String username,
        String email,
        String password
) {
}
