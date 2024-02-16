package cm.api.tools;

import cm.api.models.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
  // JPA - HIBERNATE PERMITE HACER QUERYS PERSONALIZADAS
  // ES DECIR, UN QUERY EN ESTE CASO DE MYSQL
  Page<Medico> findByActivoTrue(Pageable paginacion);
}
