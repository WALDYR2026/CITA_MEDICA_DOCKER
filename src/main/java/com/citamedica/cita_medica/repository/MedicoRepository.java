package com.citamedica.cita_medica.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;


import com.citamedica.cita_medica.Model.*;

// ==================================================
// REPOSITORIO DE MÉDICOS
// Proporciona save, findAll, findById y deleteById.
// ==================================================
public interface MedicoRepository
        extends JpaRepository<Medico, String> {

    // Consulta derivada:
    // SELECT médicos ordenados por nombre.
    List<Medico> findAllByOrderByNombreMedicoAsc();

    boolean existsByEspecialidad_CodigoEspecialidad(String codigo);

    // LAB 02: trae médicos con su especialidad en una consulta JPQL.
    @Query("select m from Medico m join fetch m.especialidad order by m.nombreMedico")
    List<Medico> listarConEspecialidad();
}

