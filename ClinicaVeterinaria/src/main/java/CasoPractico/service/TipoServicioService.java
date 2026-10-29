package CasoPractico.service;

import CasoPractico.domain.TipoServicio;
import java.util.List;
import java.util.Optional;

public interface TipoServicioService {

    List<TipoServicio> obtenerTodos();

    Optional<TipoServicio> buscarPorId(Integer id);

    void registrar(TipoServicio tipoServicio);

    void borrar(Integer id);
}
