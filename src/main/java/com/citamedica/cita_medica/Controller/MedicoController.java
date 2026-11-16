package com.citamedica.cita_medica.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.citamedica.model.Medico;
import com.citamedica.cita_medica.Model.*;
import com.citamedica.cita_medica.service.*;
//import com.citamedica.service.EspecialidadService;
//import com.citamedica.service.MedicoService;

import jakarta.validation.Valid;

// ==================================================
// CONTROLADOR WEB DEL CRUD DE MÉDICOS
// ==================================================
@Controller
public class MedicoController {

    private final MedicoService medicoService;
    private final EspecialidadService especialidadService;

    public MedicoController(
            MedicoService medicoService,
            EspecialidadService especialidadService) {

        this.medicoService = medicoService;
        this.especialidadService = especialidadService;
    }

    // --------------------------------------------------
    // LISTAR MÉDICOS
    // URL: http://localhost:8080/medicos
    // --------------------------------------------------
    @GetMapping("/medicos")
    public String listar(Model model) {

        model.addAttribute(
            "medicos",
            medicoService.listarTodos()
        );

        return "medicos/listado";
    }

    // --------------------------------------------------
    // MOSTRAR FORMULARIO NUEVO
    // --------------------------------------------------
    @GetMapping("/medicos/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("medico", new Medico());
        model.addAttribute(
            "especialidades",
            especialidadService.listarTodas()
        );
        model.addAttribute("nuevo", true);

        return "medicos/formulario";
    }

    // --------------------------------------------------
    // MOSTRAR FORMULARIO PARA EDITAR
    // --------------------------------------------------
    @GetMapping("/medicos/editar/{codigo}")
    public String editar(
            @PathVariable String codigo,
            Model model) {

        Medico medico =
                medicoService.buscarPorCodigo(codigo);

        model.addAttribute("medico", medico);
        model.addAttribute(
            "especialidades",
            especialidadService.listarTodas()
        );
        model.addAttribute("nuevo", false);

        return "medicos/formulario";
    }

    // --------------------------------------------------
    // GUARDAR NUEVO O ACTUALIZAR
    // --------------------------------------------------
    @PostMapping("/medicos/guardar")
    public String guardar(
            @Valid
            @ModelAttribute("medico") Medico medico,
            BindingResult resultado,
            @RequestParam String codigoEspecialidad,
            @RequestParam boolean nuevo,
            Model model) {

        // Normalizar el código.
        if (medico.getCodigoMedico() != null) {
            medico.setCodigoMedico(
                medico.getCodigoMedico()
                    .trim()
                    .toUpperCase()
            );
        }

        // Validar que se seleccione una especialidad.
        if (codigoEspecialidad == null
                || codigoEspecialidad.isBlank()) {

            model.addAttribute(
                "errorEspecialidad",
                "Seleccione una especialidad"
            );
        }

        // Evitar duplicar la clave primaria al crear.
        if (nuevo
                && medico.getCodigoMedico() != null
                && medicoService.existePorCodigo(
                    medico.getCodigoMedico()
                )) {

            resultado.rejectValue(
                "codigoMedico",
                "duplicado",
                "El código ya está registrado"
            );
        }

        boolean especialidadInvalida =
                codigoEspecialidad == null
                || codigoEspecialidad.isBlank();

        // Regresar al formulario si hay errores.
        if (resultado.hasErrors()
                || especialidadInvalida) {

            model.addAttribute(
                "especialidades",
                especialidadService.listarTodas()
            );
            model.addAttribute("nuevo", nuevo);
            model.addAttribute(
                "codigoEspecialidadSeleccionada",
                codigoEspecialidad
            );

            return "medicos/formulario";
        }

        medicoService.guardar(
            medico,
            codigoEspecialidad
        );

        return "redirect:/medicos?guardado";
    }

    // --------------------------------------------------
    // ELIMINAR MÉDICO
    // Se usa POST para no eliminar con un enlace GET.
    // --------------------------------------------------
    @PostMapping("/medicos/eliminar/{codigo}")
    public String eliminar(
            @PathVariable String codigo) {

        medicoService.eliminar(codigo);

        return "redirect:/medicos?eliminado";
    }
}

