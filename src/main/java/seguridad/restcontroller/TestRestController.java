package seguridad.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @GetMapping("/privado")
    public ResponseEntity<?> privado(Authentication auth) {
        return ResponseEntity.ok("OK privado. Usuario: " + auth.getName());
    }
}

