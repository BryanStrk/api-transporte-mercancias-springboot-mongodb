package seguridad.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import seguridad.model.entity.EstadoIncidencia;
import seguridad.model.entity.Incidencia;

public interface IncidenciaRepository extends MongoRepository<Incidencia, String> {

    List<Incidencia> findByCamionId(String camionId);
    // para “último mes” (se filtra por fecha service)
    List<Incidencia> findByCamionIdAndEstadoAndFechaGreaterThanEqual(
            String camionId,
            EstadoIncidencia estado,
            LocalDate fechaMin
    );
}
