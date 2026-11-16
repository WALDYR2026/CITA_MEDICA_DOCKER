package com.citamedica.cita_medica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.citamedica.cita_medica.Model.*;
//import com.citamedica.model.Especialidad;
import com.citamedica.cita_medica.repository.EspecialidadRepository;

// ==================================================
// SERVICIO DE ESPECIALIDADES
// ==================================================
@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(
            EspecialidadRepository especialidadRepository) {

        this.especialidadRepository = especialidadRepository;
    }

    // Devuelve las especialidades para el combobox.
    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }

    // Busca una especialidad por su código.
    public Especialidad buscarPorCodigo(String codigo) {
        return especialidadRepository.findById(codigo)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Especialidad inexistente: " + codigo
                    )
                );
    }
}
