package app.g_agent.user_service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.model.User;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ContextService {

	private JwtService jwtService;
	private UserDetailsService userDetailsService;
	
	public ContextService(JwtService jwtService,UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		
	}

	public User getCurrentUser(HttpServletRequest request) {

		String authHeader = request.getHeader("Authorization");

		final String jwt = authHeader.substring(7);

		String userEmail = jwtService.extractUsername(jwt);

		UserDetails user = userDetailsService.loadUserByUsername(userEmail);
		return (User) user;
	}
}
