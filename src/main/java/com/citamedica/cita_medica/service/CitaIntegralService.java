package com.citamedica.cita_medica.service;

import java.util.*;
import java.time.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.citamedica.cita_medica.Model.*;
import com.citamedica.cita_medica.dto.CitaIntegralForm;
import com.citamedica.cita_medica.repository.*;

@Service
public class CitaIntegralService {
 private final CitaRepository citas;
 private final PacienteRepository pacientes;
 private final ServicioRepository servicios;
 private final MedicoRepository medicos;
 public CitaIntegralService(CitaRepository c,PacienteRepository p,ServicioRepository s,MedicoRepository m){citas=c;pacientes=p;servicios=s;medicos=m;}
 @Transactional(readOnly=true)
 public List<Cita> listar(){return citas.listarConDetalles();}
 @Transactional(readOnly=true)
 public CitaIntegralForm formulario(String codigo){
  Cita c=citas.buscarConDetalles(codigo).orElseThrow(()->new IllegalArgumentException("Cita no encontrada"));
  CitaIntegralForm f=new CitaIntegralForm(); f.setCodigoCit(c.getCodigoCit());f.setFechaCit(c.getFechaCit());f.setHoraCit(c.getHoraCit());f.setCodigoPac(c.getPaciente().getCodigoPac());
  for(DetalleCita d:c.getDetalles()){CitaIntegralForm.Linea l=new CitaIntegralForm.Linea();l.setCodigoServ(d.getServicio().getCodigoServ());l.setCantidad(d.getCantidad());l.setHoraServicio(d.getHoraServicio());l.setFechaServicio(d.getFechaServicio());if(d.getMedico()!=null)l.setCodigoMedico(d.getMedico().getCodigoMedico());f.getLineas().add(l);}
  return f;
 }
 @Transactional
 public void guardar(CitaIntegralForm f,boolean nuevo){
  String codigo=f.getCodigoCit()==null?"":f.getCodigoCit().trim().toUpperCase();
  if(codigo.length()!=8)throw new IllegalArgumentException("El código debe tener ocho caracteres");
  if(f.getLineas()==null||f.getLineas().isEmpty())throw new IllegalArgumentException("Debe agregar al menos un servicio");
  // Fecha y hora de cabecera generadas exclusivamente en el servidor.
  Set<String> unicos=new HashSet<>();
  for(CitaIntegralForm.Linea l:f.getLineas()){
   if(l.getCodigoServ()==null||l.getCodigoServ().isBlank()||l.getCantidad()==null||l.getCantidad()<1||l.getHoraServicio()==null||l.getFechaServicio()==null)throw new IllegalArgumentException("Cada línea necesita servicio, cantidad positiva, fecha y hora programadas");
   if(!unicos.add(l.getCodigoServ()))throw new IllegalArgumentException("El servicio "+l.getCodigoServ()+" está repetido");
   if(l.getCodigoMedico()==null||l.getCodigoMedico().isBlank())
    throw new IllegalArgumentException("Seleccione un médico para cada servicio");
  }
  Cita c;
  if(nuevo){if(citas.existsById(codigo))throw new IllegalArgumentException("Código de cita duplicado");c=new Cita();c.setCodigoCit(codigo);
    LocalDateTime ahora=LocalDateTime.now(ZoneId.of("America/Lima"));
    c.setFechaCit(ahora.toLocalDate());c.setHoraCit(ahora.toLocalTime().withNano(0));}
  else c=citas.buscarConDetalles(codigo).orElseThrow(()->new IllegalArgumentException("Cita inexistente"));
  Paciente paciente=pacientes.findById(f.getCodigoPac()).orElseThrow(()->new IllegalArgumentException("Seleccione un paciente válido"));
  c.setPaciente(paciente); // En edición no se cambia la fecha ni la hora original.
  // Reutilizar líneas que ya existen, eliminar huérfanas, agregar nuevas.
  Map<String,DetalleCita> existentes=new HashMap<>();
  for(DetalleCita d:c.getDetalles())existentes.put(d.getServicio().getCodigoServ(),d);
  c.getDetalles().removeIf(d->!unicos.contains(d.getServicio().getCodigoServ()));
  for(CitaIntegralForm.Linea l:f.getLineas()){
   // Validar siempre en el backend, no confiar en el filtrado JavaScript.
   Servicio srv=servicios.findById(l.getCodigoServ()).orElseThrow(()->new IllegalArgumentException("Servicio inexistente: "+l.getCodigoServ()));
   Medico medico=medicos.findById(l.getCodigoMedico()).orElseThrow(()->new IllegalArgumentException("Médico inexistente: "+l.getCodigoMedico()));
   String espServicio=srv.getEspecialidad().getCodigoEspecialidad();
   String espMedico=medico.getEspecialidad().getCodigoEspecialidad();
   if(!espServicio.equals(espMedico))
    throw new IllegalArgumentException("La especialidad del médico no corresponde al servicio: "+srv.getNombreServ());
   DetalleCita d=existentes.get(l.getCodigoServ());
   if(d==null){
    d=new DetalleCita();d.setId(new DetalleCitaId(codigo,srv.getCodigoServ()));d.setServicio(srv);d.setMedico(medico);d.setCantidad(l.getCantidad());d.setHoraServicio(l.getHoraServicio());d.setFechaServicio(l.getFechaServicio());c.agregarDetalle(d);
   }else { d.setMedico(medico); d.setCantidad(l.getCantidad());d.setHoraServicio(l.getHoraServicio());d.setFechaServicio(l.getFechaServicio()); }
  }
  citas.saveAndFlush(c);
 }
 @Transactional public void eliminar(String id){Cita c=citas.buscarConDetalles(id).orElseThrow(()->new IllegalArgumentException("Cita inexistente"));citas.delete(c);citas.flush();}
}
