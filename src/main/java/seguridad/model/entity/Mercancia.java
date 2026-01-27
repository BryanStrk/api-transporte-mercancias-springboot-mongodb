package seguridad.model.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "mercancias")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Mercancia {
	
	@Id
    private String id;
    private String descripcion;
    private String origen;
    private String destino;
    private double pesoKg;
    private String camionAsignadoId;
    private String conductorAsignadoId;
    private LocalDate fechaEntregaEstimada;
    private EstadoMercancia estado; // PENDIENTE / ASIGNADA / ENTREGADA

    
}
