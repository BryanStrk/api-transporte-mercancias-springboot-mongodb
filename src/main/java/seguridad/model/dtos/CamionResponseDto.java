package seguridad.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@NoArgsConstructor
@Data 
@Builder
public class CamionResponseDto {

	private String id;
    private String conductorId;
    private String matricula;
    private String modelo;
    private double capacidadKg;
    private String estado;
    
}
