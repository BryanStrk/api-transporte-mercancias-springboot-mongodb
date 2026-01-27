package seguridad.restcontroller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.MercanciaRequestDto;
import seguridad.model.dtos.MercanciaResponseDto;
import seguridad.model.service.MercanciaService;




@RestController
@RequestMapping("/mercancias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MercanciaRestController {

    private final MercanciaService service;

    // CRUD
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody MercanciaRequestDto dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<MercanciaResponseDto>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @Valid @RequestBody MercanciaRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.ok("Mercancia eliminada");
    }

    // Consultas del enunciado (parte)
    @GetMapping("/pendientes")
    public ResponseEntity<List<MercanciaResponseDto>> pendientes() {
        return ResponseEntity.ok(service.listarPendientes());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MercanciaResponseDto>> buscar(
            @RequestParam(required = false) String origen,
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) Double pesoMax
    ) {
        return ResponseEntity.ok(service.buscar(origen, destino, pesoMax));
    }
    
    @GetMapping("/conductor/{conductorId}")
    public ResponseEntity<List<MercanciaResponseDto>> porConductor(@PathVariable String conductorId) {
        return ResponseEntity.ok(service.mercanciasDeConductor(conductorId));
    }

}
