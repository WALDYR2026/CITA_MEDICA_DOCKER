package com.citamedica.cita_medica.repository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.citamedica.cita_medica.Model.Cita;
public interface CitaRepository extends JpaRepository<Cita,String> {
 @Query("select distinct c from Cita c left join fetch c.detalles d left join fetch d.servicio where c.codigoCit=:id")
 Optional<Cita> buscarConDetalles(@Param("id") String id);
 @Query("select distinct c from Cita c join fetch c.paciente left join fetch c.detalles")
 List<Cita> listarConDetalles();
}
