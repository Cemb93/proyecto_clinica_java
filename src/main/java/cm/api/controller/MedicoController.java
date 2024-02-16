package cm.api.controller;

import cm.api.models.Medico;
import cm.api.record.*;
import cm.api.tools.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController // SE INDICA QUE ES UN CONTROLADOR
@RequestMapping("/medicos") // SE INDICA LA RUTA
public class MedicoController {
  @Autowired // NO SE RECOMIENDA PARA TESTING
  private MedicoRepository medicoRepository;

  @PostMapping // PARA CREAR EL OBJETO
  public ResponseEntity<DatosRespuestaMedico> registrarMedico(
          @RequestBody
          DatosRegistroMedico body,
          UriComponentsBuilder uriComponentsBuilder
  ) {
    // @RequestBody => PARA INDICAR QUE SE RECIVE EL CUERPO DE LA PETICION
    // System.out.println("BODY!!" + body);
    Medico medico = medicoRepository.save(new Medico(body));
    DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
            medico.getId(),
            medico.getNombre(),
            medico.getEmail(),
            medico.getDocumento(),
            new DatosDireccion(
                    medico.getDireccion().getCalle(),
                    medico.getDireccion().getDistrito(),
                    medico.getDireccion().getCiudad(),
                    medico.getDireccion().getNumero(),
                    medico.getDireccion().getComplemento()
            )
    );
    URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
    return ResponseEntity.created(url).body(datosRespuestaMedico);
  }

  @GetMapping
  // ACA SOLO SE MUENTRAN: "nombre", "especialidad", "documento" Y "email"
//  public List<DatosListadoMedicos> listadoMedico() {
//    return medicoRepository.findAll().stream().map(DatosListadoMedicos::new).toList();
//  }
  public ResponseEntity<Page<DatosListadoMedicos>> listadoMedico(Pageable paginacion) {
    // ACA SE IMPLEMENTA EL PAGINADO
    // EL PAGINADO COMIENZA DESDE LA PAGINA CERO(0) EN ADELANTE
    // return medicoRepository.findAll(paginacion).map(DatosListadoMedicos::new);
    return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicos::new));
  }

  @PutMapping
  @Transactional // PARA QUE LOS CAMBIOS DE LA ACTUALIZACION SURGAN EFECTO
  public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody DatosActualizarMedico body) {
    Medico medico = medicoRepository.getReferenceById(body.id());
    medico.actualizarDatos(body);
    // ACA SE RETORNA EL OBJETO QUE SE ACTUALIZO
    DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
            medico.getId(),
            medico.getNombre(),
            medico.getEmail(),
            medico.getDocumento(),
            new DatosDireccion(
                    medico.getDireccion().getCalle(),
                    medico.getDireccion().getDistrito(),
                    medico.getDireccion().getCiudad(),
                    medico.getDireccion().getNumero(),
                    medico.getDireccion().getComplemento()
            )
    );
    return ResponseEntity.ok(datosRespuestaMedico);
  }

  @DeleteMapping("/{id}")
  @Transactional // PARA QUE LOS CAMBIOS DEL DELETE LOGICO SURGAN EFECTO
  public ResponseEntity eliminarMedico(@PathVariable Long id) {
    Medico medico = medicoRepository.getReferenceById(id);
    // DELETE EN LA DB
    // medicoRepository.delete(medico);
    // DELETE LOGICO
    medico.desactivarMedico();
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id) {
    Medico medico = medicoRepository.getReferenceById(id);
    var datosMedico = new DatosRespuestaMedico(
            medico.getId(),
            medico.getNombre(),
            medico.getEmail(),
            medico.getDocumento(),
            new DatosDireccion(
                    medico.getDireccion().getCalle(),
                    medico.getDireccion().getDistrito(),
                    medico.getDireccion().getCiudad(),
                    medico.getDireccion().getNumero(),
                    medico.getDireccion().getComplemento()
            )
    );
    return ResponseEntity.ok(datosMedico);
  }
}
