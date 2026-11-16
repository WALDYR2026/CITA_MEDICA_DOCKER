package com.citamedica.cita_medica.Controller;
import com.citamedica.cita_medica.dto.CitaIntegralForm;
import com.citamedica.cita_medica.Model.Paciente;
import com.citamedica.cita_medica.service.CitaIntegralService;
import com.citamedica.cita_medica.service.ModulosService;
import jakarta.validation.Valid;
import java.time.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/citas")
public class CitaController {
 private final CitaIntegralService citas;private final ModulosService catalogos;
 public CitaController(CitaIntegralService c,ModulosService s){citas=c;catalogos=s;}
 @GetMapping public String listar(Model m){m.addAttribute("citas",citas.listar());return "citas/listado";}
 private void catalogos(Model m){m.addAttribute("pacientes",catalogos.pacientes());m.addAttribute("servicios",catalogos.servicios());m.addAttribute("medicos",catalogos.medicos());m.addAttribute("pacienteNuevo",new Paciente());}
 @GetMapping("/nuevo") public String nuevo(@RequestParam(required=false) String paciente,Model m){
  CitaIntegralForm f=new CitaIntegralForm();
  LocalDateTime ahora=LocalDateTime.now(ZoneId.of("America/Lima"));
  f.setFechaCit(ahora.toLocalDate());f.setHoraCit(ahora.toLocalTime().withNano(0));
  if(paciente!=null)f.setCodigoPac(paciente);
  m.addAttribute("form",f);m.addAttribute("nuevo",true);catalogos(m);return "citas/formulario";
 }
 @GetMapping("/editar/{id}") public String editar(@PathVariable String id,Model m){m.addAttribute("form",citas.formulario(id));m.addAttribute("nuevo",false);catalogos(m);return "citas/formulario";}
 @PostMapping("/guardar") public String guardar(@Valid @ModelAttribute("form") CitaIntegralForm f,BindingResult errors,@RequestParam boolean nuevo,Model m,RedirectAttributes flash){
  if(errors.hasErrors()){m.addAttribute("nuevo",nuevo);catalogos(m);return "citas/formulario";}
  try{citas.guardar(f,nuevo);flash.addFlashAttribute("mensaje","Cita y servicios guardados correctamente");return "redirect:/citas";}
  catch(RuntimeException e){m.addAttribute("error",e.getMessage());m.addAttribute("nuevo",nuevo);catalogos(m);return "citas/formulario";}
 }
 @PostMapping("/eliminar/{id}") public String eliminar(@PathVariable String id,RedirectAttributes flash){
  try{citas.eliminar(id);flash.addFlashAttribute("mensaje","Cita y detalles eliminados");}
  catch(RuntimeException e){flash.addFlashAttribute("error",e.getMessage());}
  return "redirect:/citas";
 }
}
