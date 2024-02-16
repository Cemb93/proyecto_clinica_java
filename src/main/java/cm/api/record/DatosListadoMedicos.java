package cm.api.record;

import cm.api.models.Medico;

public record DatosListadoMedicos(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email
) {
  // CONSTRUCTOR
  public DatosListadoMedicos(Medico medico) {
    this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getDocumento(), medico.getEmail());
  }
}
