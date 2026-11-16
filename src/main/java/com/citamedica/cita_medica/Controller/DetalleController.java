package com.citamedica.cita_medica.Controller;
import com.citamedica.cita_medica.dto.DetalleForm;import com.citamedica.cita_medica.service.ModulosService;
import jakarta.validation.Valid;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.validation.BindingResult;import org.springframework.web.bind.annotation.*;import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/detalles")
public class DetalleController {
 private final ModulosService s;public DetalleController(ModulosService s){this.s=s;}
 @GetMapping public String lista(Model m){m.addAttribute("detalles",s.detalles());return "detalles/listado";}
 @GetMapping("/nuevo") public String nuevo(Model m){DetalleForm f=new DetalleForm();m.addAttribute("form",f);m.addAttribute("nuevo",true);m.addAttribute("citas",s.citas());m.addAttribute("servicios",s.servicios());m.addAttribute("medicos",s.medicos());return "detalles/formulario";}
 @GetMapping("/editar/{c}/{srv}") public String editar(@PathVariable String c,@PathVariable String srv,Model m){var x=s.detalle(c,srv);DetalleForm f=new DetalleForm();f.setCodigoCit(c);f.setCodigoServ(srv);f.setCantidad(x.getCantidad());f.setHoraServicio(x.getHoraServicio());f.setFechaServicio(x.getFechaServicio());if(x.getMedico()!=null)f.setCodigoMedico(x.getMedico().getCodigoMedico());m.addAttribute("form",f);m.addAttribute("nuevo",false);m.addAttribute("citas",s.citas());m.addAttribute("servicios",s.servicios());m.addAttribute("medicos",s.medicos());return "detalles/formulario";}
 @PostMapping("/guardar") public String guardar(@Valid @ModelAttribute("form") DetalleForm f,BindingResult r,@RequestParam boolean nuevo,Model m,RedirectAttributes a){
 if(r.hasErrors()){m.addAttribute("nuevo",nuevo);m.addAttribute("citas",s.citas());m.addAttribute("servicios",s.servicios());m.addAttribute("medicos",s.medicos());return "detalles/formulario";}
 try{s.guardarDetalle(f,nuevo);a.addFlashAttribute("mensaje","Registro guardado");return "redirect:/detalles";}
 catch(RuntimeException ex){m.addAttribute("error",ex.getMessage());m.addAttribute("nuevo",nuevo);m.addAttribute("citas",s.citas());m.addAttribute("servicios",s.servicios());m.addAttribute("medicos",s.medicos());return "detalles/formulario";}
 }
 @PostMapping("/eliminar/{c}/{srv}") public String eliminar(@PathVariable String c,@PathVariable String srv,RedirectAttributes a){try{s.borrarDetalle(c,srv);a.addFlashAttribute("mensaje","Registro eliminado");}catch(RuntimeException ex){a.addFlashAttribute("error","No se puede eliminar el registro; compruebe las dependencias");}return "redirect:/detalles";}
}
