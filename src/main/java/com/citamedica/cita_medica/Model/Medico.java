package com.citamedica.cita_medica.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// ==================================================
// ENTIDAD MEDICO
// Representa la tabla MEDICO.
// ==================================================
@Entity
@Table(name = "MEDICO")
public class Medico {

    // --------------------------------------------------
    // CLAVE PRIMARIA DEL MÉDICO
    // Formato utilizado: MED001
    // --------------------------------------------------
    @Id
    @NotBlank(message = "Ingrese el código del médico")
    @Size(
        min = 6,
        max = 6,
        message = "El código debe contener 6 caracteres"
    )
    @Column(
        name = "CODIGO_MEDICO",
        length = 6,
        nullable = false
    )
    private String codigoMedico;

    // --------------------------------------------------
    // NOMBRE COMPLETO
    // --------------------------------------------------
    @NotBlank(message = "Ingrese el nombre del médico")
    @Size(
        min = 3,
        max = 120,
        message = "El nombre debe tener entre 3 y 120 caracteres"
    )
    @Column(
        name = "NOMBRE_MEDICO",
        length = 120,
        nullable = false
    )
    private String nombreMedico;

    // --------------------------------------------------
    // RELACIÓN MUCHOS A UNO
    // Varios médicos pueden tener una especialidad.
    // --------------------------------------------------
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
        name = "CODIGO_ESPECIALIDAD",
        nullable = false
    )
    private Especialidad especialidad;

    public Medico() {
    }

    public String getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(String codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
}
