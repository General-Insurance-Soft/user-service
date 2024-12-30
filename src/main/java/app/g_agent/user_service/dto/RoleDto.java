package app.g_agent.user_service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RoleDto {

	private Long roleId;

	@NotBlank(message = "First name is required")
	private String name;

	@NotNull(message = "Authorities list cannot be null")
	@NotEmpty(message = "Authorities list cannot be empty")
	@JsonIgnore
	private List<Long> authorities;

	@JsonIgnore
	private Long organizationId;

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

	public List<Long> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Long> authorities) {
		this.authorities = authorities;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		return "RoleDto [roleId=" + roleId + ", name=" + name + ", authorities=" + authorities + ", getRoleId()="
				+ getRoleId() + ", getName()=" + getName() + ", getAuthorities()=" + getAuthorities() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
