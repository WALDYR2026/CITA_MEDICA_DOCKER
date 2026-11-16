package com.citamedica.cita_medica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Formulario independiente de la entidad: evita asignaciones no deseadas.
public class EspecialidadForm {
    @NotBlank(message="Ingrese el código")
    @Pattern(regexp="[A-Za-z0-9]{8}",message="El código debe tener 8 caracteres alfanuméricos")
    private String codigoEspecialidad;
    @NotBlank(message="Ingrese el nombre")
    @Size(max=80,message="Máximo 80 caracteres")
    private String nombreEspecialidad;
    public String getCodigoEspecialidad(){return codigoEspecialidad;}
    public void setCodigoEspecialidad(String x){codigoEspecialidad=x;}
    public String getNombreEspecialidad(){return nombreEspecialidad;}
    public void setNombreEspecialidad(String x){nombreEspecialidad=x;}
}
