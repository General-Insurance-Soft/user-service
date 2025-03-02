package app.g_agent.user_service.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
	
	@Email(message = "Invalid Email format")
	@NotBlank(message = "Email cannot be blank")
	@NotNull(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
