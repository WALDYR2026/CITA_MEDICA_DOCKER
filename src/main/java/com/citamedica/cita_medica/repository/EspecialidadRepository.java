package com.citamedica.cita_medica.repository;

import org.springframework.data.jpa.repository.JpaRepository;



import com.citamedica.cita_medica.Model.*;


//import com.citamedica.model.Especialidad;

// ==================================================
// REPOSITORIO DE ESPECIALIDADES
// String es el tipo de la clave primaria.
// ==================================================
public interface EspecialidadRepository
        extends JpaRepository<Especialidad, String> {
}

