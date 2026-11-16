package com.citamedica.cita_medica.Controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.citamedica.cita_medica.dto.EspecialidadForm;
import com.citamedica.cita_medica.service.EspecialidadCrudService;

@Controller
@RequestMapping("/especialidades")
public class EspecialidadController {
    private final EspecialidadCrudService servicio;
    public EspecialidadController(EspecialidadCrudService servicio){this.servicio=servicio;}
    @GetMapping
    public String listar(Model m){m.addAttribute("especialidades",servicio.listar());return "especialidades/listado";}
    @GetMapping("/nuevo")
    public String nuevo(Model m){m.addAttribute("form",new EspecialidadForm());m.addAttribute("nuevo",true);return "especialidades/formulario";}
    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable String codigo,Model m){
        var e=servicio.buscar(codigo);var f=new EspecialidadForm();f.setCodigoEspecialidad(e.getCodigoEspecialidad());f.setNombreEspecialidad(e.getNombreEspecialidad());
        m.addAttribute("form",f);m.addAttribute("nuevo",false);return "especialidades/formulario";
    }
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("form") EspecialidadForm f, BindingResult r,@RequestParam boolean nuevo,Model m,RedirectAttributes flash){
        if(r.hasErrors()){m.addAttribute("nuevo",nuevo);return "especialidades/formulario";}
        try{
            if(nuevo)servicio.crear(f); else servicio.editar(f.getCodigoEspecialidad(),f.getNombreEspecialidad());
            flash.addFlashAttribute("exito","Especialidad guardada correctamente");return "redirect:/especialidades";
        } catch(IllegalArgumentException | org.springframework.dao.DataIntegrityViolationException ex){
            m.addAttribute("error",ex instanceof IllegalArgumentException?ex.getMessage():"El código o nombre ya existe");m.addAttribute("nuevo",nuevo);return "especialidades/formulario";
        }
    }
    @PostMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable String codigo,RedirectAttributes flash){
        try{servicio.eliminar(codigo);flash.addFlashAttribute("exito","Especialidad eliminada");}
        catch(IllegalArgumentException ex){flash.addFlashAttribute("error",ex.getMessage());}
        return "redirect:/especialidades";
    }
}
