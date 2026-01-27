package seguridad.model.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.InscripcionRequestDto;
import seguridad.model.dtos.InscripcionResponseDto;
import seguridad.model.entity.EstadoInscripcion;
import seguridad.model.entity.EstadoMercancia;
import seguridad.model.entity.InscripcionTransporte;
import seguridad.model.entity.Mercancia;
import seguridad.model.repository.InscripcionTransporteRepository;
import seguridad.model.repository.MercanciaRepository;
import seguridad.model.entity.Camion;
import seguridad.model.repository.CamionRepository;


@Service
@RequiredArgsConstructor
public class InscripcionTransporteServiceImpl implements InscripcionTransporteService {

    private final InscripcionTransporteRepository repo;
    private final MercanciaRepository mercanciaRepo;
    private final CamionRepository camionRepo;


    private InscripcionResponseDto toDto(InscripcionTransporte i) {
        return InscripcionResponseDto.builder()
                .id(i.getId())
                .camionId(i.getCamionId())
                .mercanciaId(i.getMercanciaId())
                .fechaInscripcion(i.getFechaInscripcion())
                .estado(i.getEstado() != null ? i.getEstado().name() : null)
                .build();
    }

    @Override
    public InscripcionResponseDto crear(InscripcionRequestDto dto) {

        // Evitar duplicados
        if (repo.existsByCamionIdAndMercanciaId(dto.getCamionId(), dto.getMercanciaId())) {
            throw new RuntimeException("Ya existe una inscripción para ese camión y esa mercancía");
        }

        // La mercancía debe existir y estar PENDIENTE
        Mercancia m = mercanciaRepo.findById(dto.getMercanciaId())
                .orElseThrow(() -> new RuntimeException("No existe mercancia con id: " + dto.getMercanciaId()));

        if (m.getEstado() != EstadoMercancia.PENDIENTE) {
            throw new RuntimeException("La mercancía no está pendiente, no se puede inscribir");
        }

        InscripcionTransporte ins = InscripcionTransporte.builder()
                .camionId(dto.getCamionId())
                .mercanciaId(dto.getMercanciaId())
                .fechaInscripcion(LocalDate.now())
                .estado(EstadoInscripcion.PENDIENTE)
                .build();

        return toDto(repo.save(ins));
    }

    @Override
    public InscripcionResponseDto buscarPorId(String id) {
        return toDto(repo.findById(id).orElseThrow(() -> new RuntimeException("No existe inscripción con id: " + id)));
    }

    @Override
    public List<InscripcionResponseDto> listarTodas() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public void eliminar(String id) {
        repo.deleteById(id);
    }

    @Override
    public List<InscripcionResponseDto> listarPorMercancia(String mercanciaId) {
        return repo.findByMercanciaId(mercanciaId).stream().map(this::toDto).toList();
    }

    @Override
    public InscripcionResponseDto aceptar(String inscripcionId) {

        InscripcionTransporte ins = repo.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("No existe inscripción con id: " + inscripcionId));

        Mercancia m = mercanciaRepo.findById(ins.getMercanciaId())
                .orElseThrow(() -> new RuntimeException("No existe mercancia con id: " + ins.getMercanciaId()));

        if (m.getEstado() != EstadoMercancia.PENDIENTE) {
            throw new RuntimeException("La mercancía ya no está pendiente");
        }

        Camion c = camionRepo.findById(ins.getCamionId())
                .orElseThrow(() -> new RuntimeException("No existe camión con id: " + ins.getCamionId()));

        // 1) aceptar la inscripción
        ins.setEstado(EstadoInscripcion.ACEPTADA);
        repo.save(ins);

        // 2) asignar mercancía -> guardar relaciones
        m.setCamionAsignadoId(c.getId());
        m.setConductorAsignadoId(c.getConductorId());
        m.setEstado(EstadoMercancia.ASIGNADA);
        mercanciaRepo.save(m);

        // 3) (opcional) rechazar el resto de inscripciones pendientes de ESA mercancía
        List<InscripcionTransporte> pendientes = repo.findByMercanciaIdAndEstado(m.getId(), EstadoInscripcion.PENDIENTE);
        for (InscripcionTransporte x : pendientes) {
            x.setEstado(EstadoInscripcion.RECHAZADA);
        }
        repo.saveAll(pendientes);

        return toDto(ins);
    }


    @Override
    public InscripcionResponseDto rechazar(String inscripcionId) {
        InscripcionTransporte ins = repo.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("No existe inscripción con id: " + inscripcionId));

        ins.setEstado(EstadoInscripcion.RECHAZADA);
        return toDto(repo.save(ins));
    }
}
