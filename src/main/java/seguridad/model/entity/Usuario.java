package seguridad.model.entity;


import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuarios")
@AllArgsConstructor 
@NoArgsConstructor
@Data 
@Builder

public class Usuario implements UserDetails {

		private static final long serialVersionUID = 1L;

		@Id
	    private String id;
	    private String nombre;
	    // Usaremos email como "username" para login
	    private String email;
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	    private String password;
	    private Rol rol;

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	    	if (rol == null) return List.of();
	        return List.of(new SimpleGrantedAuthority(rol.name()));
	    }

	    @Override
	    public String getUsername() {
	        return email;
	    }

	    @Override public boolean isAccountNonExpired() { return true; } //indica si la cuenta no esta caducada/true=la cuenta es valida(no expirada) y puede iniciar sesion
	    @Override public boolean isAccountNonLocked() { return true; } //indica si la cuenta no esta cbloqueada/true= la cuenta no esta bloqueado, puede iniciar sesion
	    @Override public boolean isCredentialsNonExpired() { return true; }//Indica si la contraseñoa no esta caducada
	    @Override public boolean isEnabled() { return true; } //Indica si el usuario esta habilitado
	    														// true = usuario activo, false = usuario deshabilitado(no puede iniciar sesion)
	}


