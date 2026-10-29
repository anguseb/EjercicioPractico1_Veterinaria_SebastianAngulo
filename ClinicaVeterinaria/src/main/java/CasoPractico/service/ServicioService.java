package CasoPractico.service;

import CasoPractico.domain.Servicio;
import java.util.List;
import java.util.Optional;

public interface ServicioService {

    List<Servicio> obtenerTodos();

    Optional<Servicio> buscarPorId(Integer id);

    void registrar(Servicio servicio);

    void borrar(Integer id);
}
