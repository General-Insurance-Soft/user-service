package app.g_agent.user_service.service;

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

	OrganizationService organizationService;
	RoleRepository roleRepository;

	public UserService(UserRepository userRepository, OrganizationService organizationService,
			RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.organizationService = organizationService;
		this.roleRepository = roleRepository;
	}

	public void createUser(UserDto userDto) {
		Organization organization = organizationService.getOrganizationById(userDto.getOrganization());
		Role role = roleRepository.getReferenceById(userDto.getRole());

		User user = new User();
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setPhone(userDto.getPhone());
		user.setRole(role);
		user.setOrganization(organization);
		user.setPassword(userDto.getPassword());
		user.setSecondName(userDto.getSecondName());
		user.setThirdName(userDto.getThirdName());
		userRepository.save(user);
	}

}