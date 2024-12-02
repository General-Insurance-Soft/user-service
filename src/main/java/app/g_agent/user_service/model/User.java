package app.g_agent.user_service.model;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "second_name")
	private String secondName;
	@Column(name = "third_name")
	private String thirdName;
	private String phone;
	private String email;
	private String password;
	@Column(nullable = false)
	private String role;

	public static class Builder {
		private UUID id;
		private String firstName;
		private String secondName;
		private String thirdName;
		private String phone;
		private String email;
		private String password;
		private String role;

		public Builder setId(UUID id) {
			this.id = id;
			return this;
		}

		public Builder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder setSecondName(String secondName) {
			this.secondName = secondName;
			return this;
		}

		public Builder setThirdName(String thirdName) {
			this.thirdName = thirdName;
			return this;
		}

		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder setRole(String role) {
			this.role = role;
			return this;
		}

		public User build() {
			User user = new User();
			user.id = this.id;
			user.firstName = this.firstName;
			user.secondName = this.secondName;
			user.thirdName = this.thirdName;
			user.phone = this.phone;
			user.email = this.email;
			user.password = this.password;
			user.role = this.role;
			return user;
		}
	}

	// write getters and setters for all my variables
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return this.secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return this.thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	
	public void setUserName(String userName) {
		this.email = userName;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}


}
