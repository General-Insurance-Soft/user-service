package app.g_agent.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/v1/auth")
//http://localhost:9000/auth-service/api/v1/login
public class AuthController {

//private final JwtService jwtService;

	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private JwtService jwtService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

		User user = authenticationService.authenticate(loginRequest);

		String jwtToken = jwtService.generateToken(user);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());

		return ResponseEntity.ok(loginResponse);

	}

	@GetMapping("/index")
	public String index() {

		return "logged in successfully";

	}

}
