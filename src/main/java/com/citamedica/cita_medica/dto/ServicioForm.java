package com.citamedica.cita_medica.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public class ServicioForm {
 @NotBlank @Size(min=8,max=8) private String codigoServ;
 @NotBlank @Size(max=120) private String nombreServ;
 @NotNull @DecimalMin("0.01") private BigDecimal precioServ;
 @NotBlank private String codigoEspecialidad;
 public String getCodigoServ(){return codigoServ;} public void setCodigoServ(String s){codigoServ=s;}
 public String getNombreServ(){return nombreServ;} public void setNombreServ(String s){nombreServ=s;}
 public BigDecimal getPrecioServ(){return precioServ;} public void setPrecioServ(BigDecimal p){precioServ=p;}
 public String getCodigoEspecialidad(){return codigoEspecialidad;} public void setCodigoEspecialidad(String s){codigoEspecialidad=s;}
}
