package com.citamedica.cita_medica.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.citamedica.cita_medica.Model.Especialidad;
import com.citamedica.cita_medica.dto.EspecialidadForm;
import com.citamedica.cita_medica.repository.EspecialidadRepository;
import com.citamedica.cita_medica.repository.MedicoRepository;

@Service
public class EspecialidadCrudService {
    private final EspecialidadRepository repo;
    private final MedicoRepository medicos;
    public EspecialidadCrudService(EspecialidadRepository repo, MedicoRepository medicos){this.repo=repo;this.medicos=medicos;}
    @Transactional(readOnly=true)
    public List<Especialidad> listar(){return repo.findAll();}
    @Transactional(readOnly=true)
    public Especialidad buscar(String codigo){return repo.findById(codigo).orElseThrow(()->new IllegalArgumentException("Especialidad no encontrada"));}
    @Transactional
    public void crear(EspecialidadForm f){
        String codigo=f.getCodigoEspecialidad().trim().toUpperCase();
        if(repo.existsById(codigo))throw new IllegalArgumentException("El código ya existe");
        Especialidad e=new Especialidad();e.setCodigoEspecialidad(codigo);e.setNombreEspecialidad(f.getNombreEspecialidad().trim());
        repo.saveAndFlush(e);
    }
    @Transactional
    public void editar(String codigo,String nombre){
        Especialidad e=buscar(codigo);e.setNombreEspecialidad(nombre.trim());
        repo.flush(); // dirty checking
    }
    @Transactional
    public void eliminar(String codigo){
        Especialidad e=buscar(codigo);
        // Evitar borrar una especialidad si se utiliza en MEDICO.
        if(medicos.existsByEspecialidad_CodigoEspecialidad(codigo))throw new IllegalArgumentException("No se puede eliminar: existen médicos vinculados");
        try{repo.delete(e);repo.flush();}
        catch(DataIntegrityViolationException ex){throw new IllegalArgumentException("No se puede eliminar: registro vinculado en otra tabla",ex);}
    }
}
