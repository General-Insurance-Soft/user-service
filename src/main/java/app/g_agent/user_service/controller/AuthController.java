package app.g_agent.user_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.g_agent.user_service.dto.LoginRequest;
import app.g_agent.user_service.dto.LoginResponse;
import app.g_agent.user_service.dto.Token;
import app.g_agent.user_service.model.User;
import app.g_agent.user_service.service.AuthenticationService;
import app.g_agent.user_service.service.JwtService;
import app.g_agent.user_service.system.commons.Message;

@RestController
@RequestMapping("/api/v1/auth")
//http://localhost:9000/auth-service/api/v1/login
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	@Value("${security.jwt.refresh-expiration-time}")
	private long jwtRefreshExpiration;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
		User user = null;
		logger.info("Authenticating request =====>");
		try {
			user = authenticationService.authenticate(loginRequest);
		} catch (Exception e) {
			Message message = new Message();
			message.setNameString("Unauthorized");
			message.setMessageString(e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
		}

		logger.info("Attempting to generate JWT =====>");
		Map<String, Object> extraClaims = new HashMap<String, Object>();
		extraClaims.put("type", "access");
		String jwtToken = jwtService.generateToken(extraClaims, user, jwtExpiration);
		extraClaims = new HashMap<String, Object>();
		extraClaims.put("type", "refresh");
		String jwtRefreshToken = jwtService.generateToken(extraClaims, user, jwtRefreshExpiration);

		LoginResponse loginResponse = new LoginResponse();

		Token jwtTokenObj = new Token.Builder().token(jwtToken).expiresIn(jwtExpiration).build();

		Token jwtRefreshTokenObj = new Token.Builder().token(jwtRefreshToken).expiresIn(jwtRefreshExpiration).build();

		loginResponse.setJwtRefreshToken(jwtRefreshTokenObj);
		loginResponse.setJwtToken(jwtTokenObj);

		logger.info("Authenticate successful =====>");

		return ResponseEntity.ok(loginResponse);

	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
		String refreshToken = request.get("refreshToken");
		ResponseEntity responseEntity = null;
		final String userEmail = jwtService.extractUsername(refreshToken);

		if (userEmail != null) {
			UserDetails user = userDetailsService.loadUserByUsername(userEmail);

			if (jwtService.isTokenValid(refreshToken, user, "refresh")) {
				Map<String, Object> extraClaims = new HashMap<String, Object>();
				extraClaims.put("type", "access");
				String jwtToken = jwtService.generateToken(extraClaims, user, jwtExpiration);
				Token jwtTokenObj = new Token.Builder().token(jwtToken).expiresIn(jwtExpiration).build();
				responseEntity = ResponseEntity.ok(jwtTokenObj);
			} else {
				Message message = new Message();
				message.setNameString("Unauthorized");
				message.setMessageString("Invalid refresh token");
				responseEntity = ResponseEntity.status(401).body(message);
			}
		}
		return responseEntity;
	}

	@GetMapping("/index")
	public String index() {

		return "logged in successfully";

	}

}
