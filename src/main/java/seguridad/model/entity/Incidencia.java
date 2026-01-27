package seguridad.model.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "incidencias")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Incidencia {

    @Id
    private String id;
    private String camionId;
    private String descripcion;
    private LocalDate fecha;
    private TipoIncidencia tipo;     // MECANICA / METEOROLOGICA / DOCUMENTAL
    private EstadoIncidencia estado; // ABIERTA / RESUELTA
}

