package app.g_agent.user_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@GetMapping("/public/hello")
	public String publicEndpoint() {
		return "This is a public endpoint.";
	}

	@GetMapping("/secure/hello")
	public String secureEndpoint() {
		return "This is a secure endpoint.";
	}
}
