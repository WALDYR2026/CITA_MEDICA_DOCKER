package com.citamedica.cita_medica.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.citamedica.cita_medica.Model.Medico;
import com.citamedica.cita_medica.Model.Especialidad;

@Service
public class EntityManagerLaboratorioService {
    public static final String ID="JP0001";
    @PersistenceContext private EntityManager em;

    // TRANSIENT -> MANAGED -> INSERT al flush.
    @Transactional
    public String persist(){
        if(em.find(Medico.class,ID)!=null)throw new IllegalArgumentException("JP0001 ya existe. Ejecute REMOVE o FIND");
        Especialidad e=em.find(Especialidad.class,"ESP00001");
        if(e==null)throw new IllegalArgumentException("Debe existir ESP00001");
        Medico m=new Medico();m.setCodigoMedico(ID);m.setNombreMedico("Prueba persist JPA");m.setEspecialidad(e);
        em.persist(m); boolean managed=em.contains(m);em.flush();
        return "PERSIST: objeto administrado="+managed+"; INSERT sincronizado.";
    }
    @Transactional(readOnly=true)
    public String find(){
        Medico m=em.find(Medico.class,ID);
        if(m==null)return "FIND: JP0001 no existe";
        return "FIND: "+m.getCodigoMedico()+" / "+m.getNombreMedico()+" / "+m.getEspecialidad().getNombreEspecialidad()+"; managed="+em.contains(m);
    }
    @Transactional
    public String dirty(){
        Medico m=required();m.setNombreMedico("Actualizado por dirty checking");em.flush();
        return "DIRTY CHECKING: UPDATE sin save ni merge";
    }
    @Transactional
    public String merge(){
        Medico original=required();em.detach(original);boolean antes=em.contains(original);
        original.setNombreMedico("Actualizado con merge");
        Medico nuevo=em.merge(original);em.flush();
        return "MERGE: original managed="+antes+"; devuelto managed="+em.contains(nuevo)+"; original managed="+em.contains(original);
    }
    @Transactional
    public String remove(){
        Medico m=required();em.remove(m);em.flush();return "REMOVE: DELETE JP0001 ejecutado";
    }
    private Medico required(){
        Medico m=em.find(Medico.class,ID);
        if(m==null)throw new IllegalArgumentException("Primero ejecute PERSIST para crear JP0001");return m;
    }
}
