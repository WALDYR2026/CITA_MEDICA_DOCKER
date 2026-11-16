package com.citamedica.cita_medica.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.citamedica.cita_medica.Model.DetalleCita;
import com.citamedica.cita_medica.Model.DetalleCitaId;
public interface DetalleCitaRepository extends JpaRepository<DetalleCita,DetalleCitaId> {}
