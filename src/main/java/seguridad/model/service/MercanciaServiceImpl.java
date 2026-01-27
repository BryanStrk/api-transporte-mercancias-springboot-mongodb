package seguridad.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.MercanciaRequestDto;
import seguridad.model.dtos.MercanciaResponseDto;
import seguridad.model.entity.EstadoMercancia;
import seguridad.model.entity.Mercancia;
import seguridad.model.repository.MercanciaRepository;

@Service
@RequiredArgsConstructor
public class MercanciaServiceImpl implements MercanciaService {

    private final MercanciaRepository repo;

    private MercanciaResponseDto toDto(Mercancia m) {
        return MercanciaResponseDto.builder()
                .id(m.getId())
                .descripcion(m.getDescripcion())
                .origen(m.getOrigen())
                .destino(m.getDestino())
                .pesoKg(m.getPesoKg())
                .fechaEntregaEstimada(m.getFechaEntregaEstimada())
                .estado(m.getEstado() != null ? m.getEstado().name() : null)
                .build();
    }

    @Override
    public MercanciaResponseDto crear(MercanciaRequestDto dto) {
        Mercancia m = Mercancia.builder()
                .descripcion(dto.getDescripcion())
                .origen(dto.getOrigen())
                .destino(dto.getDestino())
                .pesoKg(dto.getPesoKg())
                .fechaEntregaEstimada(dto.getFechaEntregaEstimada())
                .estado(EstadoMercancia.PENDIENTE) // por defecto al crear
                .build();

        return toDto(repo.save(m));
    }

    @Override
    public MercanciaResponseDto actualizar(String id, MercanciaRequestDto dto) {
        Mercancia m = repo.findById(id).orElseThrow(() -> new RuntimeException("No existe mercancia con id: " + id));

        m.setDescripcion(dto.getDescripcion());
        m.setOrigen(dto.getOrigen());
        m.setDestino(dto.getDestino());
        m.setPesoKg(dto.getPesoKg());
        m.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());

        return toDto(repo.save(m));
    }

    @Override
    public void eliminar(String id) {
        repo.deleteById(id);
    }

    @Override
    public MercanciaResponseDto buscarPorId(String id) {
        return toDto(repo.findById(id).orElseThrow(() -> new RuntimeException("No existe mercancia con id: " + id)));
    }

    @Override
    public List<MercanciaResponseDto> listarTodas() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<MercanciaResponseDto> listarPendientes() {
        return repo.findByEstado(EstadoMercancia.PENDIENTE).stream().map(this::toDto).toList();
    }

    @Override
    public List<MercanciaResponseDto> buscar(String origen, String destino, Double pesoMax) {
        return repo.buscar(origen, destino, pesoMax).stream().map(this::toDto).toList();
    }
    
    @Override
    public List<MercanciaResponseDto> mercanciasDeConductor(String conductorId) {
        List<EstadoMercancia> estados = List.of(EstadoMercancia.ASIGNADA, EstadoMercancia.ENTREGADA);

        return repo.findByConductorAsignadoIdAndEstadoIn(conductorId, estados)
                .stream()
                .map(this::toDto)
                .toList();
    }


}

