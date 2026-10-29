package CasoPractico.controllers;

import CasoPractico.domain.TipoServicio;
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
@RequestMapping("/tipos")
public class TipoServicioController {

    private final TipoServicioService tipoServicioService;
    private final MessageSource messageSource;

    public TipoServicioController(TipoServicioService tipoServicioService, MessageSource messageSource) {
        this.tipoServicioService = tipoServicioService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String mostrarLista(Model model) {
        var tipos = tipoServicioService.obtenerTodos();
        model.addAttribute("tipos", tipos);
        model.addAttribute("totalTipos", tipos.size());
        model.addAttribute("tipoServicio", new TipoServicio());
        model.addAttribute("paginaActiva", "tipos");
        return "tipos/lista";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEdicion(@PathVariable("id") Integer id, Model model, RedirectAttributes atributos) {
        Optional<TipoServicio> tipoEncontrado = tipoServicioService.buscarPorId(id);
        if (tipoEncontrado.isEmpty()) {
            atributos.addFlashAttribute("mensajeError", obtenerMensaje("tipo.error.noexiste"));
            return "redirect:/tipos";
        }
        model.addAttribute("tipoServicio", tipoEncontrado.get());
        model.addAttribute("paginaActiva", "tipos");
        return "tipos/editar";
    }

    @PostMapping("/registrar")
    public String registrarTipo(@Valid TipoServicio tipoServicio, BindingResult resultado,
            RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            atributos.addFlashAttribute("mensajeError", obtenerMensaje("tipo.error.datos"));
            return "redirect:/tipos";
        }
        boolean esNuevo = tipoServicio.getId() == null;
        tipoServicioService.registrar(tipoServicio);
        atributos.addFlashAttribute("mensajeExito",
                obtenerMensaje(esNuevo ? "tipo.agregado" : "tipo.modificado"));
        return "redirect:/tipos";
    }

    @PostMapping("/borrar")
    public String borrarTipo(@RequestParam("id") Integer id, RedirectAttributes atributos) {
        String claveAtributo = "mensajeExito";
        String claveMensaje = "tipo.eliminado";
        try {
            tipoServicioService.borrar(id);
        } catch (IllegalArgumentException e) {
            claveAtributo = "mensajeError";
            claveMensaje = "tipo.error.noexiste";
        } catch (IllegalStateException e) {
            claveAtributo = "mensajeError";
            claveMensaje = "tipo.error.asociados";
        } catch (Exception e) {
            claveAtributo = "mensajeError";
            claveMensaje = "tipo.error.general";
        }
        atributos.addFlashAttribute(claveAtributo, obtenerMensaje(claveMensaje));
        return "redirect:/tipos";
    }

    private String obtenerMensaje(String clave) {
        return messageSource.getMessage(clave, null, LocaleContextHolder.getLocale());
    }
}
