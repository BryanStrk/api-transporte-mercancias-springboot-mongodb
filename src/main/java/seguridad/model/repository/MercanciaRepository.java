package seguridad.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import seguridad.model.entity.EstadoMercancia;
import seguridad.model.entity.Mercancia;

public interface MercanciaRepository extends MongoRepository<Mercancia, String>, MercanciaRepositoryCustom {
	
	List<Mercancia> findByEstado(EstadoMercancia estado);
    List<Mercancia> findByConductorAsignadoIdAndEstadoIn(
            String conductorAsignadoId,
            List<EstadoMercancia> estados
    );

}
