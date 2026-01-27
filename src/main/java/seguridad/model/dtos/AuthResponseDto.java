package seguridad.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponseDto {
	
	private String token;
    private String email;
    private String rol; 
    
}
