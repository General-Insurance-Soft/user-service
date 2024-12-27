package app.g_agent.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthorityDto {

	private Long authorityId;

	@NotBlank(message = "Authority Name is required")
	private String name;

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
