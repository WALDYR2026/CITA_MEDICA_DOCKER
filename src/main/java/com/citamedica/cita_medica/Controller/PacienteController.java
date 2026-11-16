package com.citamedica.cita_medica.Controller;
import com.citamedica.cita_medica.Model.Paciente;
import com.citamedica.cita_medica.service.ModulosService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/pacientes")
public class PacienteController {
 private final ModulosService s;public PacienteController(ModulosService s){this.s=s;}
 @GetMapping public String lista(Model m){m.addAttribute("pacientes",s.pacientes());return "pacientes/listado";}
 @GetMapping("/nuevo") public String nuevo(Model m){m.addAttribute("paciente",new Paciente());m.addAttribute("nuevo",true);return "pacientes/formulario";}
 @GetMapping("/editar/{id}") public String editar(@PathVariable String id,Model m){m.addAttribute("paciente",s.paciente(id));m.addAttribute("nuevo",false);return "pacientes/formulario";}
 @PostMapping("/guardar") public String guardar(@Valid @ModelAttribute("paciente") Paciente p,BindingResult r,@RequestParam boolean nuevo,Model m,RedirectAttributes a){
 if(r.hasErrors()){m.addAttribute("nuevo",nuevo);return "pacientes/formulario";}
 try{s.guardarPaciente(p,nuevo);a.addFlashAttribute("mensaje","Paciente guardado");return "redirect:/pacientes";}
 catch(RuntimeException ex){m.addAttribute("error",ex.getMessage());m.addAttribute("nuevo",nuevo);return "pacientes/formulario";}
 }
 @PostMapping("/eliminar/{id}") public String eliminar(@PathVariable String id,RedirectAttributes a){try{s.borrarPaciente(id);a.addFlashAttribute("mensaje","Paciente eliminado");}catch(RuntimeException e){a.addFlashAttribute("error","No se puede eliminar: compruebe si tiene citas asociadas");}return "redirect:/pacientes";}
}
