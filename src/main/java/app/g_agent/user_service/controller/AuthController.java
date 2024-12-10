package app.g_agent.user_service.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.g_agent.user_service.service.JwtService;

@RestController
@RequestMapping("/api/v1/auth")
//http://localhost:9000/auth-service/api/v1/login
public class AuthController {

//private final JwtService jwtService;
    
//    private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
	  
	    return "logged in";

	}


    public static class LoginRequest {
        private String username;
        private String password;
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

}
