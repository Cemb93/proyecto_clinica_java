package cm.api.record;

// A ESTO SE LE CONOCE COMO DTO
public record DatosDireccion(
        String calle,
        String distrito,
        String ciudad,
        String numero,
        String complemento
) {
}
