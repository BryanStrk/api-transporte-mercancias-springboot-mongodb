package seguridad.model.service;

import java.util.List;

import seguridad.model.dtos.InscripcionRequestDto;
import seguridad.model.dtos.InscripcionResponseDto;

public interface InscripcionTransporteService {
	
    InscripcionResponseDto crear(InscripcionRequestDto dto);
    InscripcionResponseDto buscarPorId(String id);
    List<InscripcionResponseDto> listarTodas();
    void eliminar(String id);
    List<InscripcionResponseDto> listarPorMercancia(String mercanciaId);
    InscripcionResponseDto aceptar(String inscripcionId);
    InscripcionResponseDto rechazar(String inscripcionId);
    
}
