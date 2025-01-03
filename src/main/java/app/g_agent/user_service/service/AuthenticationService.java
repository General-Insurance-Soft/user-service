package app.g_agent.user_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.user_service.dto.LoginRequest;
import app.g_agent.user_service.dto.SingUpRequest;
import app.g_agent.user_service.model.Organization;
import app.g_agent.user_service.model.Role;
import app.g_agent.user_service.model.User;
import app.g_agent.user_service.repository.UserRepository;

@Service
public class AuthenticationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private AuthorityService autorityService;

	private RoleService roleService;

	private UserService userService;

	private OrganizationService organizationService;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, AuthorityService autorityService, RoleService roleService,
			OrganizationService organizationService, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.autorityService = autorityService;
		this.roleService = roleService;
		this.userService = userService;
		this.organizationService = organizationService;
	}

//	public User signup(RegisterUserDto input) {
//		User user = new User().setFullName(input.getFullName()).setEmail(input.getEmail())
//				.setPassword(passwordEncoder.encode(input.getPassword()));
//
//		return userRepository.save(user);
//	}
//new Exception()
	public User authenticate(LoginRequest input) {
		logger.info("Attempt to authenticate==========> " + input.getEmail());

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		logger.info("provided user/email ==========> " + input.getEmail());
		return userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new RuntimeException("Bad Credentials"));
	}

	@Transactional
	public Boolean signUpUser(SingUpRequest input) throws Exception {

		Organization organization = new Organization();
		Role role = roleService.getRoleById(0L);

		organization.setName("Default");
		organization.setUpdatedBy(userService.getUserById(0L));
		organizationService.createOrganization(organization);
		User user = new User();

		user.setEmail(input.getEmail());

		user.setRole(role);
		user.setOrganization(organization);
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setSecondName(input.getSecondName());
		user.setThirdName(input.getThirdName());
		user.setFirstName(input.getFirstName());
		user.setUpdatedBy(userService.getUserById(0L));
		try {
			userRepository.save(user);
			return true;
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				throw new Exception("A user with this email already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}

	}
}
