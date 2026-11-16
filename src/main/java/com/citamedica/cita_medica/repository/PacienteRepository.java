package com.citamedica.cita_medica.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.citamedica.cita_medica.Model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente,String> {}
