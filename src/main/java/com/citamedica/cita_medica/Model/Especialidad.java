package com.citamedica.cita_medica.Model;


//package com.citamedica.cita_medica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

// ==================================================
// ENTIDAD ESPECIALIDAD
// Representa la tabla ESPECIALIDAD de MySQL.
// ==================================================
@Entity
@Table(name = "ESPECIALIDAD")
public class Especialidad {

    // --------------------------------------------------
    // CLAVE PRIMARIA: CHAR(8)
    // Ejemplo: ESP00001
    // --------------------------------------------------
    @Id
    @Column(
        name = "CODIGO_ESPECIALIDAD",
        length = 8,
        nullable = false
    )
    private String codigoEspecialidad;

    // --------------------------------------------------
    // NOMBRE DE LA ESPECIALIDAD
    // --------------------------------------------------
    @Column(
        name = "NOMBRE_ESPECIALIDAD",
        length = 80,
        nullable = false
    )
    private String nombreEspecialidad;

    // LAB 02: lado inverso de la relación. NO crea otra FK ni tabla.
    @OneToMany(mappedBy = "especialidad")
    private List<Medico> medicos = new ArrayList<>();

    public List<Medico> getMedicos() { return medicos; }

    // Método de conveniencia para mantener ambos lados sincronizados.
    public void agregarMedico(Medico medico) {
        medicos.add(medico);
        medico.setEspecialidad(this);
    }

    // Constructor requerido por JPA.
    public Especialidad() {
    }

    public String getCodigoEspecialidad() {
        return codigoEspecialidad;
    }

    public void setCodigoEspecialidad(String codigoEspecialidad) {
        this.codigoEspecialidad = codigoEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }
}
