package seguridad.model.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "inscripciones_transporte")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InscripcionTransporte {

    @Id
    private String id;
    private String camionId;
    private String mercanciaId;
    private LocalDate fechaInscripcion;
    private EstadoInscripcion estado; // PENDIENTE / ACEPTADA / RECHAZADA
}
