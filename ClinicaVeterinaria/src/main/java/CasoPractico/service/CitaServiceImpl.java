package CasoPractico.service;

import CasoPractico.domain.Cita;
import CasoPractico.repository.CitaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerTodas() {
        return citaRepository.findAllByOrderByFechaAsc();
    }
}
