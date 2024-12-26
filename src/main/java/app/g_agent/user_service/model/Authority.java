package app.g_agent.user_service.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "name")
	private String authorityName;
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "updated_by")
	private Long updateBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setRoleName(String authorityName) {
		this.authorityName = authorityName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Role getrole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

}
