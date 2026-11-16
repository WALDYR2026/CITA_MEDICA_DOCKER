package com.citamedica.cita_medica.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
@Entity @Table(name="SERVICIO")
public class Servicio {
 @Id @NotBlank @Size(min=8,max=8) @Column(name="CODIGO_SERV",length=8) private String codigoServ;
 @NotBlank @Size(max=120) @Column(name="NOMBRE_SERV",length=120,nullable=false) private String nombreServ;
 @NotNull @DecimalMin(value="0.01") @Digits(integer=8,fraction=2) @Column(name="PRECIO_SERV",precision=10,scale=2,nullable=false) private BigDecimal precioServ;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="CODIGO_ESPECIALIDAD",nullable=false) private Especialidad especialidad;
 public Servicio(){}
 public String getCodigoServ(){return codigoServ;} public void setCodigoServ(String x){codigoServ=x;}
 public String getNombreServ(){return nombreServ;} public void setNombreServ(String x){nombreServ=x;}
 public BigDecimal getPrecioServ(){return precioServ;} public void setPrecioServ(BigDecimal x){precioServ=x;}
 public Especialidad getEspecialidad(){return especialidad;} public void setEspecialidad(Especialidad x){especialidad=x;}
}
