package app.g_agent.user_service.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.dto.LoginRequest;
import app.g_agent.user_service.model.User;
import app.g_agent.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

//	public User signup(RegisterUserDto input) {
//		User user = new User().setFullName(input.getFullName()).setEmail(input.getEmail())
//				.setPassword(passwordEncoder.encode(input.getPassword()));
//
//		return userRepository.save(user);
//	}

	public User authenticate(LoginRequest input) {
		logger.info("Attempt to authenticate==========> "+ input.getEmail());
		
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		logger.info("provided user/email ==========> "+ input.getEmail());
		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}
}
