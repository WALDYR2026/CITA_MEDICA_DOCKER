package com.citamedica.cita_medica.Controller;
import com.citamedica.cita_medica.Model.Paciente;
import com.citamedica.cita_medica.service.ModulosService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class PacienteCitaController {
 private final ModulosService s;
 public PacienteCitaController(ModulosService s){this.s=s;}
 @PostMapping("/citas/paciente/nuevo")
 public String guardar(@Valid @ModelAttribute Paciente paciente,BindingResult r,RedirectAttributes flash){
  if(r.hasErrors()){flash.addFlashAttribute("error","Verifique el código (6 caracteres), nombre, edad (0–120) y teléfono (9 dígitos)");return "redirect:/citas/nuevo?altaError=1";}
  try{s.guardarPaciente(paciente,true);flash.addFlashAttribute("mensaje","Paciente registrado. Puede continuar la cita.");return "redirect:/citas/nuevo?paciente="+paciente.getCodigoPac().trim().toUpperCase();}
  catch(RuntimeException e){flash.addFlashAttribute("error",e.getMessage());return "redirect:/citas/nuevo?altaError=1";}
 }
}
