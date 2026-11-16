package com.citamedica.cita_medica.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import com.citamedica.cita_medica.Model.*;
import com.citamedica.cita_medica.repository.*;
import com.citamedica.cita_medica.dto.*;
@Service
public class ModulosService {
 private final PacienteRepository pacientes; private final ServicioRepository servicios;
 private final CitaRepository citas; private final DetalleCitaRepository detalles;
 private final EspecialidadRepository especialidades; private final MedicoRepository medicos;
 public ModulosService(PacienteRepository p,ServicioRepository s,CitaRepository c,DetalleCitaRepository d,EspecialidadRepository e,MedicoRepository m){pacientes=p;servicios=s;citas=c;detalles=d;especialidades=e;medicos=m;}
 @Transactional(readOnly=true) public List<Paciente> pacientes(){return pacientes.findAll();}
 @Transactional(readOnly=true) public List<Servicio> servicios(){List<Servicio> xs=servicios.findAll();xs.forEach(x->x.getEspecialidad().getNombreEspecialidad());return xs;}
 @Transactional(readOnly=true) public List<Cita> citas(){List<Cita> xs=citas.findAll();xs.forEach(x->x.getPaciente().getNombrePac());return xs;}
 @Transactional(readOnly=true) public List<DetalleCita> detalles(){List<DetalleCita> xs=detalles.findAll();xs.forEach(x->{x.getCita().getCodigoCit();x.getServicio().getNombreServ();if(x.getMedico()!=null)x.getMedico().getNombreMedico();});return xs;}
 @Transactional(readOnly=true) public List<Medico> medicos(){return medicos.listarConEspecialidad();}
 @Transactional(readOnly=true) public List<Especialidad> especialidades(){return especialidades.findAll();}
 @Transactional(readOnly=true) public Paciente paciente(String id){return pacientes.findById(id).orElseThrow(()->new IllegalArgumentException("Paciente inexistente"));}
 @Transactional(readOnly=true) public Servicio servicio(String id){Servicio s=servicios.findById(id).orElseThrow(()->new IllegalArgumentException("Servicio inexistente"));s.getEspecialidad().getCodigoEspecialidad();return s;}
 @Transactional(readOnly=true) public Cita cita(String id){Cita c=citas.findById(id).orElseThrow(()->new IllegalArgumentException("Cita inexistente"));c.getPaciente().getCodigoPac();return c;}
 @Transactional(readOnly=true) public DetalleCita detalle(String c,String s){DetalleCita d=detalles.findById(new DetalleCitaId(c,s)).orElseThrow(()->new IllegalArgumentException("Detalle inexistente"));d.getServicio().getEspecialidad().getCodigoEspecialidad();if(d.getMedico()!=null)d.getMedico().getCodigoMedico();return d;}
 @Transactional public void guardarPaciente(Paciente datos,boolean nuevo){
 String id=datos.getCodigoPac().trim().toUpperCase();datos.setCodigoPac(id);
 if(nuevo&&pacientes.existsById(id))throw new IllegalArgumentException("Código de paciente duplicado");
 if(!nuevo&&!pacientes.existsById(id))throw new IllegalArgumentException("Paciente inexistente");
 pacientes.saveAndFlush(datos);
 }
 @Transactional public void borrarPaciente(String id){pacientes.delete(paciente(id));pacientes.flush();}
 @Transactional public void guardarServicio(ServicioForm f,boolean nuevo){
 String id=f.getCodigoServ().trim().toUpperCase();
 Servicio s=nuevo?new Servicio():servicio(id);
 if(nuevo&&servicios.existsById(id))throw new IllegalArgumentException("Código de servicio duplicado");
 if(nuevo)s.setCodigoServ(id);
 s.setNombreServ(f.getNombreServ());s.setPrecioServ(f.getPrecioServ());
 s.setEspecialidad(especialidades.findById(f.getCodigoEspecialidad()).orElseThrow(()->new IllegalArgumentException("Especialidad inexistente")));
 servicios.saveAndFlush(s);
 }
 @Transactional public void borrarServicio(String id){servicios.delete(servicio(id));servicios.flush();}
 @Transactional public void borrarCita(String id){citas.delete(cita(id));citas.flush();}
 @Transactional public void guardarDetalle(DetalleForm f,boolean nuevo){
 DetalleCitaId id=new DetalleCitaId(f.getCodigoCit(),f.getCodigoServ());
 if(nuevo&&detalles.existsById(id))throw new IllegalArgumentException("Este servicio ya figura en la cita");
 DetalleCita d=nuevo?new DetalleCita():detalles.findById(id).orElseThrow(()->new IllegalArgumentException("Detalle inexistente"));
 d.setId(id);d.setCita(citas.findById(f.getCodigoCit()).orElseThrow(()->new IllegalArgumentException("Cita inexistente")));
 d.setServicio(servicios.findById(f.getCodigoServ()).orElseThrow(()->new IllegalArgumentException("Servicio inexistente")));
 Servicio srv=d.getServicio();Medico medico=medicos.findById(f.getCodigoMedico()).orElseThrow(()->new IllegalArgumentException("Médico inexistente"));
 String esp=srv.getEspecialidad().getCodigoEspecialidad();
 if(!esp.equals(medico.getEspecialidad().getCodigoEspecialidad()))throw new IllegalArgumentException("Especialidad del médico y del servicio no coinciden");
 d.setMedico(medico);d.setCantidad(f.getCantidad());d.setHoraServicio(f.getHoraServicio());d.setFechaServicio(f.getFechaServicio());detalles.saveAndFlush(d);
 }
 @Transactional public void borrarDetalle(String c,String s){
  long cantidad=detalles.findAll().stream().filter(x->x.getCita().getCodigoCit().equals(c)).count();
  if(cantidad<=1)throw new IllegalArgumentException("Una cita debe conservar al menos un servicio; elimine la cita completa si corresponde");
  detalles.delete(detalle(c,s));detalles.flush();
 }
}
