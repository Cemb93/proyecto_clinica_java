package cm.api.record;

  public record DatosActualizarMedico(
          Long id,
          String nombre,
          String documento,
          DatosDireccion direccion
  ) {
}
