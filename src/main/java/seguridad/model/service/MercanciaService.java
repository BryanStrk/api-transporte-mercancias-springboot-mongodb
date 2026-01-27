package seguridad.model.service;

import java.util.List;

import seguridad.model.dtos.MercanciaRequestDto;
import seguridad.model.dtos.MercanciaResponseDto;

public interface MercanciaService {
    MercanciaResponseDto crear(MercanciaRequestDto dto);
    MercanciaResponseDto actualizar(String id, MercanciaRequestDto dto);
    void eliminar(String id);
    MercanciaResponseDto buscarPorId(String id);
    List<MercanciaResponseDto> listarTodas();
    List<MercanciaResponseDto> listarPendientes();
    List<MercanciaResponseDto> buscar(String origen, String destino, Double pesoMax);
    List<MercanciaResponseDto> mercanciasDeConductor(String conductorId);

}
