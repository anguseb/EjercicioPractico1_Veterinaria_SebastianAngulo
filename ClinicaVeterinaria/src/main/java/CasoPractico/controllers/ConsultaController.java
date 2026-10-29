package CasoPractico.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final MessageSource messageSource;

    public ConsultaController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("paginaActiva", "consultas");
        return "consultas/formulario";
    }

    // Solo interfaz: la consulta no se guarda en la base de datos
    @PostMapping("/enviar")
    public String recibirConsulta(@RequestParam("nombre") String nombre,
            @RequestParam("correo") String correo,
            @RequestParam("nombreMascota") String nombreMascota,
            @RequestParam("mensaje") String mensaje,
            RedirectAttributes atributos) {
        String agradecimiento = messageSource.getMessage("consulta.gracias",
                new Object[]{nombre}, LocaleContextHolder.getLocale());
        atributos.addFlashAttribute("mensajeExito", agradecimiento);
        return "redirect:/consultas";
    }
}
