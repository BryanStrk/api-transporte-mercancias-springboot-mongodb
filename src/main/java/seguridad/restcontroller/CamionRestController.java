package seguridad.restcontroller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.CamionRequestDto;
import seguridad.model.dtos.CamionResponseDto;
import seguridad.model.entity.Usuario;
import seguridad.model.service.CamionService;

@RestController
@RequestMapping("/camiones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CamionRestController {

    private final CamionService service;

    // CONDUCTOR: crear camión (usa el id del usuario logueado)
    @PostMapping
    public ResponseEntity<CamionResponseDto> crear(@AuthenticationPrincipal Usuario usuario,
                                                  @RequestBody CamionRequestDto dto) {
        return ResponseEntity.ok(service.crear(usuario.getId(), dto));
    }

    // CONDUCTOR: mis camiones
    @GetMapping("/mios")
    public ResponseEntity<List<CamionResponseDto>> mios(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.misCamiones(usuario.getId()));
    }

    // CONDUCTOR: mis camiones disponibles
    @GetMapping("/mios/disponibles")
    public ResponseEntity<List<CamionResponseDto>> misDisponibles(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.misDisponibles(usuario.getId()));
    }

    // ENUNCIADO (EMPRESA): disponibles de un conductor concreto
    @GetMapping("/conductor/{conductorId}/disponibles")
    public ResponseEntity<List<CamionResponseDto>> disponiblesDeConductor(@PathVariable String conductorId) {
        return ResponseEntity.ok(service.disponiblesDeConductor(conductorId));
    }

    // extra (útil): ver camion por id (autenticado)
    @GetMapping("/{id}")
    public ResponseEntity<CamionResponseDto> porId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // CONDUCTOR: actualizar su camion
    @PutMapping("/{id}")
    public ResponseEntity<CamionResponseDto> actualizar(@AuthenticationPrincipal Usuario usuario,
                                                        @PathVariable String id,
                                                        @RequestBody CamionRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, usuario.getId(), dto));
    }

    // CONDUCTOR: borrar su camion
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@AuthenticationPrincipal Usuario usuario, @PathVariable String id) {
        service.eliminar(id, usuario.getId());
        return ResponseEntity.ok("Camión eliminado");
    }
}

