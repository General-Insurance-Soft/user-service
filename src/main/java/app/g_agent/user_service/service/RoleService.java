package app.g_agent.user_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.dto.RoleDto;
import app.g_agent.user_service.model.Authority;
import app.g_agent.user_service.model.Role;
import app.g_agent.user_service.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class RoleService {

	private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

	private RoleRepository roleRepository;
	private AuthorityService authorityService;
	private ContextService contextService;

	public RoleService(RoleRepository roleRepository, AuthorityService authorityService,
			UserDetailsService userDetailsService, ContextService contextService) {
		this.roleRepository = roleRepository;
		this.authorityService = authorityService;
		this.contextService = contextService;
	}

	public Role getRoleById(Long id) {
		Role role = roleRepository.getReferenceById(id);
		return role;
	}

	public void createRole(HttpServletRequest request, RoleDto roleDto) throws Exception {
		List<Authority> authorities = authorityService.getAuthorityByIds(roleDto.getAuthorities());

		Role role = new Role();
		role.setAuthorities(authorities);
		role.setName(roleDto.getName());
		role.setUpdatedBy(contextService.getCurrentUser(request));
		logger.info("Role to persist ==========> " + role);
		try {
			roleRepository.save(role);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				throw new Exception("This role already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}

	}
}
