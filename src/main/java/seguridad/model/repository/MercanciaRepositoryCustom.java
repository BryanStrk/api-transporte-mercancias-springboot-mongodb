package seguridad.model.repository;

import java.util.List;

import seguridad.model.entity.Mercancia;

public interface MercanciaRepositoryCustom {
	
    List<Mercancia> buscar(String origen, String destino, Double pesoMax);
    
}
