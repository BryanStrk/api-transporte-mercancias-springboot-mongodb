package seguridad.model.repository;
	
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import seguridad.model.entity.Camion;
import seguridad.model.entity.EstadoCamion;

public interface CamionRepository extends MongoRepository<Camion, String> {

	    List<Camion> findByConductorId(String conductorId);
	    List<Camion> findByConductorIdAndEstado(String conductorId, EstadoCamion estado);

}
