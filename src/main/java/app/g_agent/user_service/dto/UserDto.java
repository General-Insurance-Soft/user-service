package app.g_agent.user_service.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import app.g_agent.user_service.model.Organization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank(message = "First name is required")
	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("second_name")
	private String secondName;

	@NotBlank(message = "Third name cannot be blank")
	@NotNull(message = "Third name is required")
	@JsonProperty("third_name")
	private String thirdName;

	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^(?:\\+254|0)(7|1)[0-9]{8}$", message = "Invalid  phone number. Must start with +254 or 0, followed by 7 or 1, and 8 digits.")
	private String phone;

	@Email(message = "Invalid Email format")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@NotNull(message = "Role is required")
	private Long role;

	@NotNull(message = "Organization field required")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long organization;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public Long getOrganization() {
		return organization;
	}

	public void setOrganization(Long organization) {
		this.organization = organization;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserDto [firstName=" + firstName + ", secondName=" + secondName + ", thirdName=" + thirdName
				+ ", phone=" + phone + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", organization=" + organization + "]";
	}

}
