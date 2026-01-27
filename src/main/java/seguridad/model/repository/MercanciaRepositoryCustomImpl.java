package seguridad.model.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import seguridad.model.entity.Mercancia;


@Repository
@RequiredArgsConstructor
public class MercanciaRepositoryCustomImpl  implements MercanciaRepositoryCustom{
	
	private final MongoTemplate mongoTemplate;
	
	@Override
	public List<Mercancia> buscar(String origen, String destino, Double pesoMax) {
        List<Criteria> criterios = new ArrayList<>();

        if (origen != null && !origen.isBlank()) {
            criterios.add(Criteria.where("origen").regex(origen, "i"));
        }
        if (destino != null && !destino.isBlank()) {
            criterios.add(Criteria.where("destino").regex(destino, "i"));
        }
        if (pesoMax != null) {
            criterios.add(Criteria.where("pesoKg").lte(pesoMax));
        }

        Query q = new Query();
        if (!criterios.isEmpty()) {
            q.addCriteria(new Criteria().andOperator(criterios.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(q, Mercancia.class);
    }

}
