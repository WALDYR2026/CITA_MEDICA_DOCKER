package com.citamedica.cita_medica.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.*;
import java.util.*;

/** Cabecera y líneas del formulario: todos los detalles se guardan juntos. */
public class CitaIntegralForm {
 @NotBlank @Size(min=8,max=8) private String codigoCit;
 @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) private LocalDate fechaCit;
 @DateTimeFormat(pattern="HH:mm") private LocalTime horaCit;
 @NotBlank private String codigoPac;
 @Valid private List<Linea> lineas=new ArrayList<>();
 public static class Linea {
  @NotBlank private String codigoServ;
  @NotBlank private String codigoMedico;
  @NotNull @Min(1) private Integer cantidad=1;
  @NotNull @DateTimeFormat(pattern="HH:mm") private LocalTime horaServicio;
  @NotNull @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) private LocalDate fechaServicio;
 public String getCodigoMedico(){return codigoMedico;} public void setCodigoMedico(String v){codigoMedico=v;}
  public LocalDate getFechaServicio(){return fechaServicio;}
  public void setFechaServicio(LocalDate v){fechaServicio=v;}
  public LocalTime getHoraServicio(){return horaServicio;}
  public void setHoraServicio(LocalTime v){horaServicio=v;}
  public String getCodigoServ(){return codigoServ;}
  public void setCodigoServ(String v){codigoServ=v;}
  public Integer getCantidad(){return cantidad;}
  public void setCantidad(Integer v){cantidad=v;}
 }
 public String getCodigoCit(){return codigoCit;}
 public void setCodigoCit(String v){codigoCit=v;}
 public LocalDate getFechaCit(){return fechaCit;}
 public void setFechaCit(LocalDate v){fechaCit=v;}
 public LocalTime getHoraCit(){return horaCit;}
 public void setHoraCit(LocalTime v){horaCit=v;}
 public String getCodigoPac(){return codigoPac;}
 public void setCodigoPac(String v){codigoPac=v;}
 public List<Linea> getLineas(){return lineas;}
 public void setLineas(List<Linea> v){lineas=v;}
}
