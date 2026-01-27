package seguridad.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "camiones")
@AllArgsConstructor 
@NoArgsConstructor
@Data 
@Builder
public class Camion {
	
	@Id
	private String id;
	private String conductorId;   // id del Usuario (CONDUCTOR)
    private String matricula;
    private String modelo;
    private double capacidadKg;
    private EstadoCamion estado;  // ACTIVO / INACTIVO

}
