package seguridad.model.service;

import java.util.List;

import seguridad.model.dtos.IncidenciaRequestDto;
import seguridad.model.dtos.IncidenciaResponseDto;

public interface IncidenciaService {
    IncidenciaResponseDto crear(IncidenciaRequestDto dto);
    IncidenciaResponseDto actualizar(String id, IncidenciaRequestDto dto);
    void eliminar(String id);

    IncidenciaResponseDto buscarPorId(String id);
    List<IncidenciaResponseDto> listarTodas();

    // consulta enunciado
    List<IncidenciaResponseDto> activasUltimoMesPorCamion(String camionId);

    // extra útil: cerrar incidencia
    IncidenciaResponseDto resolver(String id);
}
