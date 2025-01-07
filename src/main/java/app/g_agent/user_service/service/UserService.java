package app.g_agent.user_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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

	public void createUser(HttpServletRequest request, UserDto userDto) throws Exception {
		Organization organization = contextService.getCurrentUser(request).getOrganization();
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
		user.setUpdatedBy(contextService.getCurrentUser(request));

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

	public User getUserById(Long id) {
		return userRepository.getReferenceById(id);
	}

	public UserDto getUser(HttpServletRequest request, Long id) throws Exception {
		User currentUser = contextService.getCurrentUser(request);

		Optional<User> user = userRepository.findByIdAndOrganizationId(id, currentUser.getOrganization().getId());

		logger.info("To get user ==========> id: " + id);

		if (user.isPresent()) {
			UserDto userDto = new UserDto();
			userDto.setId(user.get().getId());
			userDto.setEmail(user.get().getEmail());
			userDto.setFirstName(user.get().getFirstName());
			userDto.setPhone(user.get().getPhone());
			userDto.setRole(user.get().getRole().getId());
			userDto.setSecondName(user.get().getSecondName());
			userDto.setThirdName(user.get().getThirdName());
			return userDto;
		} else {
			throw new Exception("The user does not exists.");
		}

	}

	public List<UserDto> getUser(HttpServletRequest request) throws Exception {
		User currentUser = contextService.getCurrentUser(request);

		Optional<List<User>> users = userRepository.findByOrganizationId(currentUser.getOrganization().getId());

		if (users.isPresent()) {

			List<UserDto> UserDtos = new ArrayList<>();

			users.get().forEach(user -> {

				UserDto userDto = new UserDto();
				userDto.setId(user.getId());
				userDto.setEmail(user.getEmail());
				userDto.setFirstName(user.getFirstName());
				userDto.setPhone(user.getPhone());
				userDto.setRole(user.getRole().getId());
				userDto.setSecondName(user.getSecondName());
				userDto.setThirdName(user.getThirdName());
				UserDtos.add(userDto);

			});

			return UserDtos;

		} else {
			throw new Exception("No users exist.");
		}

	}

	public void updateUser(HttpServletRequest request, UserDto userDto, Long id) throws Exception {

		User currentUser = contextService.getCurrentUser(request);
		User user = new User();

		user.setId(id);
		user.setUpdatedBy(contextService.getCurrentUser(request));

		// role
		if (userDto.getRole() == null) {
			user.setRole(currentUser.getRole());

		} else {
			Role role = roleRepository.getReferenceById(userDto.getRole());
			user.setRole(role);
		}

		// email
		if (userDto.getEmail() == null) {
			user.setEmail(currentUser.getEmail());

		} else {
			if (userDto.getEmail().isBlank()) {
				throw new Exception("The value of email is not valid");
			}

			String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
			Pattern pattern = Pattern.compile(regex);
			String email = userDto.getEmail();
			if (!pattern.matcher(email).matches()) {
				throw new Exception("Invalid  email format");
			}
			user.setEmail(userDto.getEmail());
		}

		// first name
		if (userDto.getFirstName() == null) {

			user.setFirstName(currentUser.getFirstName());
		} else {

			if (userDto.getFirstName().isBlank()) {
				throw new Exception("The value of firstname is not valid");
			}

			user.setFirstName(userDto.getFirstName());
		}

		// phone
		if (userDto.getPhone() == null) {
			user.setPhone(currentUser.getPhone());
		} else {
			if (userDto.getPhone().isBlank()) {
				throw new Exception("The value of phone number is not valid");
			}

			String regex = "^(?:\\+254|0)(7|1)[0-9]{8}$";
			Pattern pattern = Pattern.compile(regex);
			String phone = userDto.getPhone();
			if (!pattern.matcher(phone).matches()) {
				throw new Exception(
						"Invalid  phone number. Must start with +254 or 0, followed by 7 or 1, and 8 digits.");
			}

			user.setPhone(userDto.getPhone());
		}

		// organization
		if (userDto.getOrganization() == null) {
			user.setOrganization(currentUser.getOrganization());

		} else {
			user.setOrganization(organizationService.getOrganizationById(userDto.getOrganization()));
		}

		// password
		if (userDto.getPassword() == null) {

			user.setPassword(currentUser.getPassword());
		} else {

			if (userDto.getPassword().isBlank()) {
				throw new Exception("The value of password is not valid");
			}
			if (userDto.getPassword().length() < 8) {
				throw new Exception("Password must be at least 8 characters long");
			}

			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}

		// second name
		if (userDto.getSecondName() == null) {

			user.setSecondName(currentUser.getSecondName());
		} else {

			if (userDto.getSecondName().isBlank()) {
				throw new Exception("The value of second name is not valid");
			}

			user.setSecondName(userDto.getSecondName());
		}

		// third name
		if (userDto.getThirdName() == null) {

			user.setThirdName(currentUser.getThirdName());
		} else {

			if (userDto.getThirdName().isBlank()) {
				throw new Exception("The value of third name is not valid");
			}

			user.setThirdName(userDto.getThirdName());
		}

		userRepository.save(user);

	}

}