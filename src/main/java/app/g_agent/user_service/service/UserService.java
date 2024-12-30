package app.g_agent.user_service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.dto.UserDto;
import app.g_agent.user_service.model.Organization;
import app.g_agent.user_service.model.Role;
import app.g_agent.user_service.model.User;
import app.g_agent.user_service.repository.RoleRepository;
import app.g_agent.user_service.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {// implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	OrganizationService organizationService;
	RoleRepository roleRepository;
	private ContextService contextService;

	public UserService(UserRepository userRepository, OrganizationService organizationService,
			RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, ContextService contextService) {
		this.userRepository = userRepository;
		this.organizationService = organizationService;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.contextService = contextService;

	}

	public void createUser(UserDto userDto) throws Exception {
		Organization organization = organizationService.getOrganizationById(userDto.getOrganization());
		Role role = roleRepository.getReferenceById(userDto.getRole());

		User user = new User();
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setPhone(userDto.getPhone());
		user.setRole(role);
		user.setOrganization(organization);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setSecondName(userDto.getSecondName());
		user.setThirdName(userDto.getThirdName());
		user.setFirstName(userDto.getFirstName());

		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				throw new Exception("A user with this email already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}

	}

	public void deleteUser(HttpServletRequest request, Long id) throws Exception {
		User currentUser = contextService.getCurrentUser(request);

		Optional<User> user = userRepository.findByIdAndOrganizationId(id, currentUser.getOrganization().getId());

		logger.info("To delete user ==========> id: " + id);
		if (user.isPresent()) {
			userRepository.delete(user.get());
		} else {
			throw new Exception("The user does not exists.");
		}

	}

}