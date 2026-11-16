package com.citamedica.cita_medica.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="PACIENTE")
public class Paciente {
 @Id @NotBlank @Size(min=6,max=6) @Column(name="CODIGO_PAC",length=6) private String codigoPac;
 @NotBlank @Size(max=100) @Column(name="NOMBRE_PAC",length=100,nullable=false) private String nombrePac;
 @NotNull @Min(0) @Max(120) @Column(name="EDAD_PAC",nullable=false) private Integer edadPac;
 @NotBlank @Pattern(regexp="[0-9]{9}") @Column(name="TELEFONO_PAC",length=9,nullable=false) private String telefonoPac;
 public Paciente(){}
 public String getCodigoPac(){return codigoPac;} public void setCodigoPac(String x){codigoPac=x;}
 public String getNombrePac(){return nombrePac;} public void setNombrePac(String x){nombrePac=x;}
 public Integer getEdadPac(){return edadPac;} public void setEdadPac(Integer x){edadPac=x;}
 public String getTelefonoPac(){return telefonoPac;} public void setTelefonoPac(String x){telefonoPac=x;}
}
