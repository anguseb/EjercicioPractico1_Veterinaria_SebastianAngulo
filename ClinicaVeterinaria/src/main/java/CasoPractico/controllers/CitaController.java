package CasoPractico.controllers;

import CasoPractico.service.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public String mostrarLista(Model model) {
        var citas = citaService.obtenerTodas();
        model.addAttribute("citas", citas);
        model.addAttribute("totalCitas", citas.size());
        model.addAttribute("paginaActiva", "citas");
        return "citas/lista";
    }
}
