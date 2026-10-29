package CasoPractico.repository;

import CasoPractico.domain.TipoServicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoServicioRepository extends JpaRepository<TipoServicio, Integer> {

    List<TipoServicio> findAllByOrderByNombreAsc();
}
