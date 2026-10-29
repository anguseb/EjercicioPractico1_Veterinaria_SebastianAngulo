package CasoPractico.controllers;

import CasoPractico.domain.Servicio;
import CasoPractico.domain.TipoServicio;
import CasoPractico.service.ServicioService;
import CasoPractico.service.TipoServicioService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService servicioService;
    private final TipoServicioService tipoServicioService;
    private final MessageSource messageSource;

    public ServicioController(ServicioService servicioService, TipoServicioService tipoServicioService,
            MessageSource messageSource) {
        this.servicioService = servicioService;
        this.tipoServicioService = tipoServicioService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String mostrarLista(Model model) {
        var servicios = servicioService.obtenerTodos();
        model.addAttribute("servicios", servicios);
        model.addAttribute("totalServicios", servicios.size());
        model.addAttribute("tipos", tipoServicioService.obtenerTodos());
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("paginaActiva", "servicios");
        return "servicios/lista";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEdicion(@PathVariable("id") Integer id, Model model, RedirectAttributes atributos) {
        Optional<Servicio> servicioEncontrado = servicioService.buscarPorId(id);
        if (servicioEncontrado.isEmpty()) {
            atributos.addFlashAttribute("mensajeError", obtenerMensaje("servicio.error.noexiste"));
            return "redirect:/servicios";
        }
        model.addAttribute("servicio", servicioEncontrado.get());
        model.addAttribute("tipos", tipoServicioService.obtenerTodos());
        model.addAttribute("paginaActiva", "servicios");
        return "servicios/editar";
    }

    @PostMapping("/registrar")
    public String registrarServicio(@Valid Servicio servicio, BindingResult resultado,
            @RequestParam(value = "idTipoServicio", required = false) Integer idTipoServicio,
            RedirectAttributes atributos) {
        Optional<TipoServicio> tipoElegido = (idTipoServicio == null)
                ? Optional.empty()
                : tipoServicioService.buscarPorId(idTipoServicio);
        if (resultado.hasErrors() || tipoElegido.isEmpty()) {
            atributos.addFlashAttribute("mensajeError", obtenerMensaje("servicio.error.datos"));
            return "redirect:/servicios";
        }
        boolean esNuevo = servicio.getId() == null;
        servicio.setTipoServicio(tipoElegido.get());
        servicioService.registrar(servicio);
        atributos.addFlashAttribute("mensajeExito",
                obtenerMensaje(esNuevo ? "servicio.agregado" : "servicio.modificado"));
        return "redirect:/servicios";
    }

    @PostMapping("/borrar")
    public String borrarServicio(@RequestParam("id") Integer id, RedirectAttributes atributos) {
        String claveAtributo = "mensajeExito";
        String claveMensaje = "servicio.eliminado";
        try {
            servicioService.borrar(id);
        } catch (IllegalArgumentException e) {
            claveAtributo = "mensajeError";
            claveMensaje = "servicio.error.noexiste";
        } catch (IllegalStateException e) {
            claveAtributo = "mensajeError";
            claveMensaje = "servicio.error.asociados";
        } catch (Exception e) {
            claveAtributo = "mensajeError";
            claveMensaje = "servicio.error.general";
        }
        atributos.addFlashAttribute(claveAtributo, obtenerMensaje(claveMensaje));
        return "redirect:/servicios";
    }

    private String obtenerMensaje(String clave) {
        return messageSource.getMessage(clave, null, LocaleContextHolder.getLocale());
    }
}
