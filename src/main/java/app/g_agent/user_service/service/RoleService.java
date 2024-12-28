package app.g_agent.user_service.service;

import org.springframework.stereotype.Service;

import app.g_agent.user_service.model.Role;
import app.g_agent.user_service.repository.RoleRepository;

@Service
public class RoleService {

	RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public Role getOrganizationById(Long id) {
		Role role = roleRepository.getReferenceById(id);
		return role;
	}
}
