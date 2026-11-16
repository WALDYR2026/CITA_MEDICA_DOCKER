package com.citamedica.cita_medica.Model;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
@Entity @Table(name="CITA")
public class Cita {
 @Id @Column(name="CODIGO_CIT",length=8) private String codigoCit;
 @Column(name="FECHA_CIT",nullable=false) private LocalDate fechaCit;
 @Column(name="HORA_CIT",nullable=false) private LocalTime horaCit;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="CODIGO_PAC",nullable=false) private Paciente paciente;
 @OneToMany(mappedBy="cita",cascade=CascadeType.ALL,orphanRemoval=true)
 private List<DetalleCita> detalles=new ArrayList<>();
 public List<DetalleCita> getDetalles(){return detalles;}
 public void agregarDetalle(DetalleCita d){detalles.add(d);d.setCita(this);}
 public void quitarDetalle(DetalleCita d){detalles.remove(d);d.setCita(null);}
 public Cita(){}
 public String getCodigoCit(){return codigoCit;} public void setCodigoCit(String x){codigoCit=x;}
 public LocalDate getFechaCit(){return fechaCit;} public void setFechaCit(LocalDate x){fechaCit=x;}
 public LocalTime getHoraCit(){return horaCit;} public void setHoraCit(LocalTime x){horaCit=x;}
 public Paciente getPaciente(){return paciente;} public void setPaciente(Paciente x){paciente=x;}
}
