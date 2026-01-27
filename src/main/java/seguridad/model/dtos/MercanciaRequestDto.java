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
public class MercanciaRequestDto {
	
	private String descripcion;
	private String origen;
	private String destino;
	private double pesoKg;
	private LocalDate fechaEntregaEstimada;

}
