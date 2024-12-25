package app.g_agent.user_service.dto;

import org.springframework.stereotype.Component;

public class RefreshTokenResponse {

	Token jwtToken;

	public Token getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(Token jwtToken) {
		this.jwtToken = jwtToken;
	}

}