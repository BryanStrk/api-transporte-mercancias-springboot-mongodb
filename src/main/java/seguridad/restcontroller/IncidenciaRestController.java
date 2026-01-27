package seguridad.restcontroller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.IncidenciaRequestDto;
import seguridad.model.dtos.IncidenciaResponseDto;
import seguridad.model.service.IncidenciaService;

@RestController
@RequestMapping("/incidencias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IncidenciaRestController {

    private final IncidenciaService service;

    // CRUD
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody IncidenciaRequestDto dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<IncidenciaResponseDto>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody IncidenciaRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.ok("Incidencia eliminada");
    }

    // Consulta enunciado
    @GetMapping("/camion/{camionId}/activas-ultimo-mes")
    public ResponseEntity<List<IncidenciaResponseDto>> activasUltimoMes(@PathVariable String camionId) {
        return ResponseEntity.ok(service.activasUltimoMesPorCamion(camionId));
    }

    // Extra útil (cerrar)
    @PutMapping("/{id}/resolver")
    public ResponseEntity<?> resolver(@PathVariable String id) {
        return ResponseEntity.ok(service.resolver(id));
    }
}

