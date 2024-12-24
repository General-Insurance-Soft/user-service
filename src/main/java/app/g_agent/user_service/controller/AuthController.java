package app.g_agent.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.g_agent.user_service.dto.LoginRequest;
import app.g_agent.user_service.dto.LoginResponse;
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
	Message message;

	@PostMapping("/authenticate")
	public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
		User user = null;
		logger.info("Authenticating request =====>");
		try {
			user = authenticationService.authenticate(loginRequest);
		} catch (Exception e) {
			message.setNameString("Unauthorized");
			message.setMessageString(e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
		}

		logger.info("Attempting to generate JWT =====>");
		String jwtToken = jwtService.generateToken(user);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());
		logger.info("Authenticate successful =====>");

		return ResponseEntity.ok(loginResponse);

	}

	@GetMapping("/index")
	public String index() {

		return "logged in successfully";

	}

}
