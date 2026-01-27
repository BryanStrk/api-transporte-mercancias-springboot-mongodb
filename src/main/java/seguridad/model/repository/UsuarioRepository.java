package seguridad.model.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import seguridad.model.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	
	Optional<Usuario> findByEmail(String email);
	boolean existsByEmail(String email);

}
