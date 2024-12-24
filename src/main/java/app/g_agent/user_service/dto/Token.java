package app.g_agent.user_service.dto;

import org.springframework.stereotype.Component;

public class Token {
	private String token;
	private long expiresIn;

	public String getToken() {
		return token;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public void setToken(String token) {
		this.token = token;
	}

	// Builder class
	public static class Builder {
		private String token;
		private long expiresIn;

		public Builder() {
		}

		public Builder token(String token) {
			this.token = token;
			return this;
		}

		public Builder expiresIn(long expiresIn) {
			this.expiresIn = expiresIn;
			return this;
		}

		public Token build() {
			Token tokenInstance = new Token();
			tokenInstance.setToken(this.token);
			tokenInstance.setExpiresIn(this.expiresIn);
			return tokenInstance;
		}
	}
}
