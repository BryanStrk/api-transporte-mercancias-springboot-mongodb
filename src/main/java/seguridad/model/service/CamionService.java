package seguridad.model.service;

import java.util.List;
import seguridad.model.dtos.CamionRequestDto;
import seguridad.model.dtos.CamionResponseDto;

public interface CamionService {
	

	  CamionResponseDto crear(String conductorId, CamionRequestDto dto);
	  CamionResponseDto actualizar(String camionId, String conductorId, CamionRequestDto dto);
	  void eliminar(String camionId, String conductorId);
	  CamionResponseDto buscarPorId(String camionId);
	  List<CamionResponseDto> misCamiones(String conductorId);
	  List<CamionResponseDto> misDisponibles(String conductorId);
	    // CONSULTA DEL ENUNCIADO (EMPRESA):
	  List<CamionResponseDto> disponiblesDeConductor(String conductorId);
}



