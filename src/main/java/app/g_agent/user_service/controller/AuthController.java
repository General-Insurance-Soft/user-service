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
import app.g_agent.user_service.dto.UserTokenResponse;
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

	@Value("${security.jwt.access-token-type}")
	private String accessTokenType;

	@Value("${security.jwt.refresh-token-type}")
	private String refreshTokenType;

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

		UserTokenResponse userTokenResponse = jwtService.getTokenUserResponse(user);
		logger.info("Authenticate successful =====>");
		return ResponseEntity.ok(userTokenResponse);

	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
		logger.info("Refresh token request =====> " + request);
		String refreshToken = request.get("refresh-token");
		ResponseEntity<?> responseEntity = null;

		try {
			final String userEmail = jwtService.extractUsername(refreshToken);

			if (userEmail != null) {
				UserDetails user = userDetailsService.loadUserByUsername(userEmail);

				if (jwtService.isTokenValid(refreshToken, user, refreshTokenType)) {
					UserTokenResponse userTokenResponse = jwtService.getTokenUserResponse(user);
					jwtService.persistUsedToken(refreshToken);
					return ResponseEntity.ok(userTokenResponse);
				} else {
					Message message = new Message();
					message.setNameString("Unauthorized");
					message.setMessageString("Invalid refresh token");
					responseEntity = ResponseEntity.status(401).body(message);
				}
			}
			return responseEntity;
		} catch (Exception e) {
			Message message = new Message();
			message.setNameString("Unauthorized");
			message.setMessageString(e.getMessage());
			return ResponseEntity.status(400).body(message);
		}

	}

	@PostMapping("/validate-token")
	public ResponseEntity<?> validateToken(@RequestBody Map<String, String> request) {
		logger.info("Refresh token request =====> " + request);
		String token = request.get("token");
		ResponseEntity<?> responseEntity = null;

		try {
			final String userEmail = jwtService.extractUsername(token);

			if (userEmail != null) {
				UserDetails user = userDetailsService.loadUserByUsername(userEmail);

				if (jwtService.isTokenValid(token, user, accessTokenType)) {
					UserTokenResponse userTokenResponse = jwtService.getTokenUserResponse(user);
					Message message=new Message();
					message.setNameString("success");
					message.setMessageString("valid token");
					return ResponseEntity.ok(message);
				} else {
					Message message = new Message();
					message.setNameString("Unauthorized");
					message.setMessageString("Invalid refresh token");
					responseEntity = ResponseEntity.status(401).body(message);
				}
			}
			return responseEntity;
		} catch (Exception e) {
			Message message = new Message();
			message.setNameString("Unauthorized");
			message.setMessageString(e.getMessage());
			return ResponseEntity.status(400).body(message);
		}

	}

	@GetMapping("/index")
	public String index() {

		return "logged in successfully";

	}

}
