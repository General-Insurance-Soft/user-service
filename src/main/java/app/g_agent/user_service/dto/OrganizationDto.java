package app.g_agent.user_service.dto;

import java.time.LocalDateTime;

import app.g_agent.user_service.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrganizationDto {

	private Long id;

	@NotNull(message = "Organization name required")
	@NotBlank(message = "Organization name required")
	private String name;

	private Long address;

	private String phone;

	@NotNull(message = "Organization email required")
	@NotBlank(message = "Organization email required")
	private String email;

	private String website;

	private String logo;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private User updatedBy;

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getWebsite() {
		return website;
	}

	public String getLogo() {
		return logo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getAddress() {
		return address;
	}

	public void setAddress(Long address) {
		this.address = address;
	}

}
