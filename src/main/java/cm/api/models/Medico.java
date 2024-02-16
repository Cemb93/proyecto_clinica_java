package cm.api.models;

import cm.api.record.DatosActualizarMedico;
import cm.api.record.DatosRegistroMedico;
import cm.api.tools.Especialidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor // PARA CONSTRUCTORES POR DEFECTO
@AllArgsConstructor // PARA CONSTRUCTORES CON PARAMETROS
@EqualsAndHashCode(of = "id") // PARA LAS COMPARACIONES ENTRE MEDICOS
public class Medico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String email;
  private String documento;
  @Enumerated(EnumType.STRING)
  private Especialidad especialidad;
  @Embedded
  private Direccion direccion;
  private Boolean activo;

  public Medico(DatosRegistroMedico body) {
    this.nombre = body.nombre();
    this.email = body.email();
    this.documento = body.documento();
    this.especialidad = body.especialidad();
    this.direccion = new Direccion(body.direccion());
    // CUANDO SE CREA POR DEFECTO ESTARÁ ACTIVO
    this.activo = true;
  }

  public void actualizarDatos(DatosActualizarMedico body) {
    if (body.nombre() != null) {
      this.nombre = body.nombre();
    }
    if (body.documento() != null) {
      this.documento = body.documento();
    }
    if (body.direccion() != null) {
      this.direccion = direccion.actualizarDatos(body.direccion());
    }
  }

  public void desactivarMedico() {
    this.activo = false;
  }
}
