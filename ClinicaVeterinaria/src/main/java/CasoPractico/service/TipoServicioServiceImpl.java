package CasoPractico.service;

import CasoPractico.domain.TipoServicio;
import CasoPractico.repository.TipoServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoServicioServiceImpl implements TipoServicioService {

    private final TipoServicioRepository tipoServicioRepository;

    public TipoServicioServiceImpl(TipoServicioRepository tipoServicioRepository) {
        this.tipoServicioRepository = tipoServicioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoServicio> obtenerTodos() {
        return tipoServicioRepository.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoServicio> buscarPorId(Integer id) {
        return tipoServicioRepository.findById(id);
    }

    @Override
    @Transactional
    public void registrar(TipoServicio tipoServicio) {
        tipoServicioRepository.save(tipoServicio);
    }

    @Override
    @Transactional
    public void borrar(Integer id) {
        if (!tipoServicioRepository.existsById(id)) {
            throw new IllegalArgumentException("El tipo de servicio con id " + id + " no existe.");
        }
        try {
            tipoServicioRepository.deleteById(id);
            // flush fuerza la validación de la llave foránea dentro del try
            tipoServicioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("El tipo de servicio tiene servicios asociados.", e);
        }
    }
}
