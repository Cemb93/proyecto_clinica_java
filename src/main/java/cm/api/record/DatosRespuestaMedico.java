package cm.api.record;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String documento,
        DatosDireccion direccion
) {
}
