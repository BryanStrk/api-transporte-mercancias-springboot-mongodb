package seguridad.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {
	
	@NotBlank
    private String nombre;

    @Email 
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // "EMPRESA" o "CONDUCTOR" 
    @NotBlank
    private String rol;
}


