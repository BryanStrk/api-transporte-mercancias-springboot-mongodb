package seguridad.model.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InscripcionResponseDto {
	
    private String id;
    private String camionId;
    private String mercanciaId;
    private LocalDate fechaInscripcion;
    private String estado;
    
}
