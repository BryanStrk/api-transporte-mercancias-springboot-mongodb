package seguridad.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IncidenciaRequestDto {
    private String camionId;
    private String descripcion;
    private String tipo;   // "MECANICA" / "METEOROLOGICA" / "DOCUMENTAL"
}
