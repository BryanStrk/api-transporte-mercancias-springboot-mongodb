package seguridad.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@NoArgsConstructor
@Data 
@Builder
public class CamionRequestDto {
	
	private String matricula;
    private String modelo;
    private double capacidadKg;
    private String estado; // "ACTIVO" / "INACTIVO"

}
