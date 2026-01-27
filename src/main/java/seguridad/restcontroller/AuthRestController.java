package seguridad.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import seguridad.model.dtos.AuthResponseDto;
import seguridad.model.dtos.LoginRequestDto;
import seguridad.model.dtos.RegisterRequestDto;
import seguridad.model.entity.Rol;
import seguridad.model.entity.Usuario;
import seguridad.model.repository.UsuarioRepository;
import seguridad.security.JwtSecurityService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthRestController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtSecurityService jwtSecurityService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto req) {

        if (usuarioRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Ese email ya existe");
        }

        Rol rol = req.getRol().equalsIgnoreCase("EMPRESA") ? Rol.ROLE_EMPRESA : Rol.ROLE_CONDUCTOR;

        Usuario u = Usuario.builder()
                .nombre(req.getNombre())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .rol(rol)
                .build();

        usuarioRepository.save(u);

        String token = jwtSecurityService.generateToken(u.getEmail(), u.getAuthorities());

        return ResponseEntity.ok(AuthResponseDto.builder()
                .token(token)
                .email(u.getEmail())
                .rol(u.getRol().name())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        Usuario user = (Usuario) auth.getPrincipal();
        String token = jwtSecurityService.generateToken(user.getEmail(), user.getAuthorities());

        return ResponseEntity.ok(AuthResponseDto.builder()
                .token(token)
                .email(user.getEmail())
                .rol(user.getRol().name())
                .build());
    }
}

