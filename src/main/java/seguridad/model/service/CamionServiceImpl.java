package seguridad.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.CamionRequestDto;
import seguridad.model.dtos.CamionResponseDto;
import seguridad.model.entity.Camion;
import seguridad.model.entity.EstadoCamion;
import seguridad.model.repository.CamionRepository;

@Service
@RequiredArgsConstructor
public class CamionServiceImpl implements CamionService {

    private final CamionRepository repo;

    private CamionResponseDto toDto(Camion c) {
        return CamionResponseDto.builder()
                .id(c.getId())
                .conductorId(c.getConductorId())
                .matricula(c.getMatricula())
                .modelo(c.getModelo())
                .capacidadKg(c.getCapacidadKg())
                .estado(c.getEstado() != null ? c.getEstado().name() : null)
                .build();
    }

    private EstadoCamion parseEstado(String estado) {
        if (estado == null || estado.isBlank()) return EstadoCamion.ACTIVO;
        return EstadoCamion.valueOf(estado.toUpperCase());
    }

    @Override
    public CamionResponseDto crear(String conductorId, CamionRequestDto dto) {
        Camion c = Camion.builder()
                .conductorId(conductorId)
                .matricula(dto.getMatricula())
                .modelo(dto.getModelo())
                .capacidadKg(dto.getCapacidadKg())
                .estado(parseEstado(dto.getEstado()))
                .build();

        return toDto(repo.save(c));
    }

    @Override
    public CamionResponseDto actualizar(String camionId, String conductorId, CamionRequestDto dto) {
        Camion c = repo.findById(camionId)
                .orElseThrow(() -> new RuntimeException("No existe camión con id: " + camionId));
        if (!c.getConductorId().equals(conductorId)) {
            throw new RuntimeException("No puedes editar un camión que no es tuyo");
        }

        c.setMatricula(dto.getMatricula());
        c.setModelo(dto.getModelo());
        c.setCapacidadKg(dto.getCapacidadKg());
        c.setEstado(parseEstado(dto.getEstado()));

        return toDto(repo.save(c));
    }

    @Override
    public void eliminar(String camionId, String conductorId) {
        Camion c = repo.findById(camionId)
                .orElseThrow(() -> new RuntimeException("No existe camión con id: " + camionId));

        if (!c.getConductorId().equals(conductorId)) {
            throw new RuntimeException("No puedes borrar un camión que no es tuyo");
        }

        repo.deleteById(camionId);
    }

    @Override
    public CamionResponseDto buscarPorId(String camionId) {
        return toDto(repo.findById(camionId)
                .orElseThrow(() -> new RuntimeException("No existe camión con id: " + camionId)));
    }
    
    @Override
    public List<CamionResponseDto> misCamiones(String conductorId) {
        return repo.findByConductorId(conductorId).stream().map(this::toDto).toList();
    }

    @Override
    public List<CamionResponseDto> misDisponibles(String conductorId) {
        return repo.findByConductorIdAndEstado(conductorId, EstadoCamion.ACTIVO).stream().map(this::toDto).toList();
    }

    @Override
    public List<CamionResponseDto> disponiblesDeConductor(String conductorId) {
        return repo.findByConductorIdAndEstado(conductorId, EstadoCamion.ACTIVO).stream().map(this::toDto).toList();
    }
}

