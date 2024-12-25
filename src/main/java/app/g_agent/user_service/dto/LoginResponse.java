package app.g_agent.user_service.dto;

import org.springframework.stereotype.Component;

public class LoginResponse {

	Token jwtToken;
	Token jwtRefreshToken;

	public Token getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(Token jwtToken) {
		this.jwtToken = jwtToken;
	}

	public Token getJwtRefreshToken() {
		return jwtRefreshToken;
	}

	public void setJwtRefreshToken(Token jwtRefreshToken) {
		this.jwtRefreshToken = jwtRefreshToken;
	}

}