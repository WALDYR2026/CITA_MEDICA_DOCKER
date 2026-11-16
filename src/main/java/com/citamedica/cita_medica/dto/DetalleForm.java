package com.citamedica.cita_medica.dto;
import jakarta.validation.constraints.*;
import java.time.LocalTime;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
public class DetalleForm {
 @NotBlank private String codigoCit;
 @NotBlank private String codigoServ;
 @NotBlank private String codigoMedico;
 @NotNull @Min(1) private Integer cantidad;
 @NotNull @DateTimeFormat(pattern="HH:mm") private LocalTime horaServicio;
 @NotNull @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) private LocalDate fechaServicio;
 public String getCodigoMedico(){return codigoMedico;} public void setCodigoMedico(String v){codigoMedico=v;}
 public LocalDate getFechaServicio(){return fechaServicio;} public void setFechaServicio(LocalDate x){fechaServicio=x;}
 public String getCodigoCit(){return codigoCit;}public void setCodigoCit(String x){codigoCit=x;}
 public String getCodigoServ(){return codigoServ;}public void setCodigoServ(String x){codigoServ=x;}
 public LocalTime getHoraServicio(){return horaServicio;}public void setHoraServicio(LocalTime x){horaServicio=x;}
 public Integer getCantidad(){return cantidad;}public void setCantidad(Integer x){cantidad=x;}
}
