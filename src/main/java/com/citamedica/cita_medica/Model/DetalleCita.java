package com.citamedica.cita_medica.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalTime;
import java.time.LocalDate;
@Entity @Table(name="DETALLE_CITA")
public class DetalleCita {
 @EmbeddedId private DetalleCitaId id;
 @MapsId("codigoCit") @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="CODIGO_CIT") private Cita cita;
 @MapsId("codigoServ") @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="CODIGO_SERV") private Servicio servicio;
 @NotNull @Min(1) @Column(name="CANTIDAD",nullable=false) private Integer cantidad;
 @Column(name="HORA_SERV") private LocalTime horaServicio;
 @Column(name="FECHA_SERVICIO") private LocalDate fechaServicio;
 // Médico asignado a la línea; nullable para conservar registros anteriores.
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="CODIGO_MEDICO") private Medico medico;
 public Medico getMedico(){return medico;} public void setMedico(Medico v){medico=v;}
 public DetalleCita(){}
 public DetalleCitaId getId(){return id;} public void setId(DetalleCitaId x){id=x;}
 public Cita getCita(){return cita;} public void setCita(Cita x){cita=x;}
 public Servicio getServicio(){return servicio;} public void setServicio(Servicio x){servicio=x;}
 public LocalDate getFechaServicio(){return fechaServicio;} public void setFechaServicio(LocalDate x){fechaServicio=x;}
 public LocalTime getHoraServicio(){return horaServicio;} public void setHoraServicio(LocalTime x){horaServicio=x;}
 public Integer getCantidad(){return cantidad;} public void setCantidad(Integer x){cantidad=x;}
}
