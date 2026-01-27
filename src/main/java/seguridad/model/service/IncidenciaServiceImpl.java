package seguridad.model.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.IncidenciaRequestDto;
import seguridad.model.dtos.IncidenciaResponseDto;
import seguridad.model.entity.EstadoIncidencia;
import seguridad.model.entity.Incidencia;
import seguridad.model.entity.TipoIncidencia;
import seguridad.model.repository.IncidenciaRepository;

@Service
@RequiredArgsConstructor
public class IncidenciaServiceImpl implements IncidenciaService {

    private final IncidenciaRepository repo;

    private IncidenciaResponseDto toDto(Incidencia i) {
        return IncidenciaResponseDto.builder()
                .id(i.getId())
                .camionId(i.getCamionId())
                .descripcion(i.getDescripcion())
                .fecha(i.getFecha())
                .tipo(i.getTipo() != null ? i.getTipo().name() : null)
                .estado(i.getEstado() != null ? i.getEstado().name() : null)
                .build();
    }

    @Override
    public IncidenciaResponseDto crear(IncidenciaRequestDto dto) {
        TipoIncidencia tipo = TipoIncidencia.valueOf(dto.getTipo().toUpperCase());

        Incidencia i = Incidencia.builder()
                .camionId(dto.getCamionId())
                .descripcion(dto.getDescripcion())
                .tipo(tipo)
                .fecha(LocalDate.now())
                .estado(EstadoIncidencia.ABIERTA)
                .build();

        return toDto(repo.save(i));
    }

    @Override
    public IncidenciaResponseDto actualizar(String id, IncidenciaRequestDto dto) {
        Incidencia i = repo.findById(id).orElseThrow(() -> new RuntimeException("No existe incidencia con id: " + id));

        i.setCamionId(dto.getCamionId());
        i.setDescripcion(dto.getDescripcion());
        i.setTipo(TipoIncidencia.valueOf(dto.getTipo().toUpperCase()));

        return toDto(repo.save(i));
    }

    @Override
    public void eliminar(String id) {
        repo.deleteById(id);
    }

    @Override
    public IncidenciaResponseDto buscarPorId(String id) {
        return toDto(repo.findById(id).orElseThrow(() -> new RuntimeException("No existe incidencia con id: " + id)));
    }

    @Override
    public List<IncidenciaResponseDto> listarTodas() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<IncidenciaResponseDto> activasUltimoMesPorCamion(String camionId) {
        LocalDate haceUnMes = LocalDate.now().minusMonths(1);
        return repo.findByCamionIdAndEstadoAndFechaGreaterThanEqual(camionId, EstadoIncidencia.ABIERTA, haceUnMes)
                .stream().map(this::toDto).toList();
    }

    @Override
    public IncidenciaResponseDto resolver(String id) {
        Incidencia i = repo.findById(id).orElseThrow(() -> new RuntimeException("No existe incidencia con id: " + id));
        i.setEstado(EstadoIncidencia.RESUELTA);
        return toDto(repo.save(i));
    }
}
