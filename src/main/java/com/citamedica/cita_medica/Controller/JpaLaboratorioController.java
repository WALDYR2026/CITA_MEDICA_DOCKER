package com.citamedica.cita_medica.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.citamedica.cita_medica.service.EntityManagerLaboratorioService;

@Controller
@RequestMapping("/laboratorio-jpa")
public class JpaLaboratorioController {
    private final EntityManagerLaboratorioService servicio;
    public JpaLaboratorioController(EntityManagerLaboratorioService servicio){this.servicio=servicio;}
    @GetMapping public String inicio(){return "laboratorio-jpa";}
    @PostMapping("/{operacion}")
    public String ejecutar(@PathVariable String operacion,RedirectAttributes flash){
        try{
            String r=switch(operacion){
                case "persist"->servicio.persist();case "find"->servicio.find();case "dirty"->servicio.dirty();
                case "merge"->servicio.merge();case "remove"->servicio.remove();
                default->throw new IllegalArgumentException("Operación desconocida");};
            flash.addFlashAttribute("resultado",r);
        }catch(IllegalArgumentException ex){flash.addFlashAttribute("error",ex.getMessage());}
        return "redirect:/laboratorio-jpa";
    }
}
