package seguridad.restcontroller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.InscripcionRequestDto;
import seguridad.model.dtos.InscripcionResponseDto;
import seguridad.model.service.InscripcionTransporteService;

@RestController
@RequestMapping("/inscripciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InscripcionTransporteRestController {

    private final InscripcionTransporteService service;

    // CRUD
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody InscripcionRequestDto dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<InscripcionResponseDto>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.ok("Inscripción eliminada");
    }

    // consulta enunciado
    @GetMapping("/mercancia/{mercanciaId}")
    public ResponseEntity<List<InscripcionResponseDto>> porMercancia(@PathVariable String mercanciaId) {
        return ResponseEntity.ok(service.listarPorMercancia(mercanciaId));
    }

    // acciones EMPRESA
    @PutMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptar(@PathVariable String id) {
        return ResponseEntity.ok(service.aceptar(id));
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(@PathVariable String id) {
        return ResponseEntity.ok(service.rechazar(id));
    }
}
