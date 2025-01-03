package app.g_agent.user_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SingUpRequest {
	private String email;
	private String password;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("second_name")
	private String secondName;

	@JsonProperty("third_name")
	private String thirdName;

	private String phone;

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

}
