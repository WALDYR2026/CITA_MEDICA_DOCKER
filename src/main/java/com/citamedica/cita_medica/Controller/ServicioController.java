package com.citamedica.cita_medica.Controller;
import com.citamedica.cita_medica.dto.ServicioForm;import com.citamedica.cita_medica.service.ModulosService;
import jakarta.validation.Valid;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.validation.BindingResult;import org.springframework.web.bind.annotation.*;import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/servicios")
public class ServicioController {
 private final ModulosService s;public ServicioController(ModulosService s){this.s=s;}
 @GetMapping public String lista(Model m){m.addAttribute("servicios",s.servicios());return "servicios/listado";}
 @GetMapping("/nuevo") public String nuevo(Model m){ServicioForm f=new ServicioForm();m.addAttribute("form",f);m.addAttribute("nuevo",true);m.addAttribute("especialidades",s.especialidades());return "servicios/formulario";}
 @GetMapping("/editar/{id}") public String editar(@PathVariable String id,Model m){var x=s.servicio(id);ServicioForm f=new ServicioForm();f.setCodigoServ(x.getCodigoServ());f.setNombreServ(x.getNombreServ());f.setPrecioServ(x.getPrecioServ());f.setCodigoEspecialidad(x.getEspecialidad().getCodigoEspecialidad()); m.addAttribute("form",f);m.addAttribute("nuevo",false);m.addAttribute("especialidades",s.especialidades());return "servicios/formulario";}
 @PostMapping("/guardar") public String guardar(@Valid @ModelAttribute("form") ServicioForm f,BindingResult r,@RequestParam boolean nuevo,Model m,RedirectAttributes a){
 if(r.hasErrors()){m.addAttribute("nuevo",nuevo);m.addAttribute("especialidades",s.especialidades());return "servicios/formulario";}
 try{s.guardarServicio(f,nuevo);a.addFlashAttribute("mensaje","Registro guardado");return "redirect:/servicios";}
 catch(RuntimeException ex){m.addAttribute("error",ex.getMessage());m.addAttribute("nuevo",nuevo);m.addAttribute("especialidades",s.especialidades());return "servicios/formulario";}
 }
 @PostMapping("/eliminar/{id}") public String eliminar(@PathVariable String id,RedirectAttributes a){try{s.borrarServicio(id);a.addFlashAttribute("mensaje","Registro eliminado");}catch(RuntimeException ex){a.addFlashAttribute("error","No se puede eliminar el registro; compruebe las dependencias");}return "redirect:/servicios";}
}
