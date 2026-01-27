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
public class IncidenciaResponseDto {
    private String id;
    private String camionId;
    private String descripcion;
    private LocalDate fecha;
    private String tipo;
    private String estado;
}
