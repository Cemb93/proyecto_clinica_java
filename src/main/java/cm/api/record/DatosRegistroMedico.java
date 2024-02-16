package cm.api.record;

import cm.api.tools.Especialidad;

// A ESTO SE LE CONOCE COMO DTO
public record DatosRegistroMedico(
        String nombre,
        String email,
        String documento,
        Especialidad especialidad,
        DatosDireccion direccion
) {
}
