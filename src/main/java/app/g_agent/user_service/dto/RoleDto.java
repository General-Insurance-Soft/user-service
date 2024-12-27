package app.g_agent.user_service.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RoleDto {

	private Long roleId;

	@NotBlank(message = "First name is required")
	private String name;

	@NotNull(message = "Authorities list cannot be null")
	@NotEmpty(message = "Authorities list cannot be empty")
	@Valid // Ensure validation is applied to each element in the list
	private List<AuthorityDto> authorities;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AuthorityDto> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityDto> authorities) {
		this.authorities = authorities;
	}

}
