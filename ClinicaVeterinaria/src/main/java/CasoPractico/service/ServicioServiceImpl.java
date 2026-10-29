package CasoPractico.service;

import CasoPractico.domain.Servicio;
import CasoPractico.repository.ServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> buscarPorId(Integer id) {
        return servicioRepository.findById(id);
    }

    @Override
    @Transactional
    public void registrar(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    @Override
    @Transactional
    public void borrar(Integer id) {
        if (!servicioRepository.existsById(id)) {
            throw new IllegalArgumentException("El servicio con id " + id + " no existe.");
        }
        try {
            servicioRepository.deleteById(id);
            // flush fuerza la validación de la llave foránea dentro del try
            servicioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("El servicio tiene citas asociadas.", e);
        }
    }
}
