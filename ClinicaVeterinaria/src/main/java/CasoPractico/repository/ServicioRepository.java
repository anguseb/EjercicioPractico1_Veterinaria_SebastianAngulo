package CasoPractico.repository;

import CasoPractico.domain.Servicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    List<Servicio> findAllByOrderByNombreAsc();
}
