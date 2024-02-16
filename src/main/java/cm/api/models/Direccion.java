package cm.api.models;

import cm.api.record.DatosDireccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// ACA SE IMPLEMENTA LA PERSISTENCIA
@Getter
@NoArgsConstructor // PARA CONSTRUCTORES POR DEFECTO
@AllArgsConstructor // PARA CONSTRUCTORES CON PARAMETROS
public class Direccion {
  private String calle;
  private String numero;
  private String complemento;
  private String ciudad;
  private String distrito;

  public Direccion(DatosDireccion direccion) {
    this.calle = direccion.calle();
    this.numero = direccion.numero();
    this.complemento = direccion.complemento();
    this.ciudad = direccion.ciudad();
    this.distrito = direccion.distrito();
  }

  public Direccion actualizarDatos(DatosDireccion direccion) {
    this.calle = direccion.calle();
    this.numero = direccion.numero();
    this.complemento = direccion.complemento();
    this.ciudad = direccion.ciudad();
    this.distrito = direccion.distrito();
    return this;
  }
}
