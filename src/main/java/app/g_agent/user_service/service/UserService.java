package app.g_agent.user_service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import app.g_agent.user_service.model.User;
import app.g_agent.user_service.repository.UserRepository;

//@Component
public class UserService {//implements UserDetailsService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepository.findByEmail(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
//				.password(user.getPassword()).roles(user.getRole())
//				// .roles(user.getRoles().split(","))
//				.build();
//	}

}