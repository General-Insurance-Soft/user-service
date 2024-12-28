package app.g_agent.user_service.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.dto.UserDto;
import app.g_agent.user_service.model.Organization;
import app.g_agent.user_service.model.Role;
import app.g_agent.user_service.model.User;
import app.g_agent.user_service.repository.RoleRepository;
import app.g_agent.user_service.repository.UserRepository;

@Service
public class UserService {// implements UserDetailsService {

	private final UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	OrganizationService organizationService;
	RoleRepository roleRepository;

	public UserService(UserRepository userRepository, OrganizationService organizationService,
			RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.organizationService = organizationService;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
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

}