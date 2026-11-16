package com.citamedica.cita_medica.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.citamedica.cita_medica.model.Especialidad;
//import com.citamedica.cita_medica.model.Medico;

import com.citamedica.cita_medica.Model.*;
import com.citamedica.cita_medica.repository.MedicoRepository;

// ==================================================
// SERVICIO DE MÉDICOS
// Contiene las operaciones del CRUD.
// ==================================================
@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadService especialidadService;

    public MedicoService(
            MedicoRepository medicoRepository,
            EspecialidadService especialidadService) {

        this.medicoRepository = medicoRepository;
        this.especialidadService = especialidadService;
    }

    // --------------------------------------------------
    // READ: LISTAR
    // --------------------------------------------------
    @Transactional(readOnly = true)
    public List<Medico> listarTodos() {
        return medicoRepository.findAllByOrderByNombreMedicoAsc();
    }

    // --------------------------------------------------
    // READ: BUSCAR POR CÓDIGO
    // --------------------------------------------------
    @Transactional(readOnly = true)
    public Medico buscarPorCodigo(String codigo) {
        return medicoRepository.findById(codigo)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "No existe el médico " + codigo
                    )
                );
    }

    // --------------------------------------------------
    // COMPROBAR SI EL CÓDIGO YA EXISTE
    // --------------------------------------------------
    @Transactional(readOnly = true)
    public boolean existePorCodigo(String codigo) {
        return medicoRepository.existsById(codigo);
    }

    // --------------------------------------------------
    // CREATE / UPDATE
    // Se recupera la especialidad administrada por JPA.
    // --------------------------------------------------
    @Transactional
    public Medico guardar(
            Medico medico,
            String codigoEspecialidad) {

        Especialidad especialidad =
                especialidadService.buscarPorCodigo(
                    codigoEspecialidad
                );

        medico.setEspecialidad(especialidad);

        return medicoRepository.save(medico);
    }

    // --------------------------------------------------
    // DELETE
    // --------------------------------------------------
    @Transactional
    public void eliminar(String codigo) {

        if (!medicoRepository.existsById(codigo)) {
            throw new IllegalArgumentException(
                "No existe el médico " + codigo
            );
        }

        medicoRepository.deleteById(codigo);
    }
}

