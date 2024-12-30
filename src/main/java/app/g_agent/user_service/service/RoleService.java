package app.g_agent.user_service.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.dto.RoleDto;
import app.g_agent.user_service.model.Authority;
import app.g_agent.user_service.model.Role;
import app.g_agent.user_service.model.User;
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
		Set<Authority> authorities = authorityService.getAuthorityByIds(roleDto.getAuthorities());
		User user = contextService.getCurrentUser(request);
		Role role = new Role();
		roleDto.getAuthorities().forEach(item -> logger
				.debug("Authorities passed from request=====> " + item + " and fected size " + authorities.size()));

		logger.info("Set role values ==========> ");
		role.setAuthority(authorities);
		role.setName(roleDto.getName());
		role.setUpdatedBy(user);
		role.setOrganization(user.getOrganization());
		logger.info("Role to persist ==========> " + role.toString());
		try {
			roleRepository.save(role);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Role error ==========> id: " + ex.getMessage());
				throw new Exception("This role already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}

	}

	public void updateRole(HttpServletRequest request, RoleDto roleDto, Long id) throws Exception {

		User user = contextService.getCurrentUser(request);
		Role currentRole = this.getRoleById(id);
		Role role = new Role();

		logger.info("Role values to update ==========> " + roleDto.toString());
		role.setId(id);

		// authorities
		if (roleDto.getAuthorities() == null) {

			role.setAuthority(currentRole.getAuthority());
		} else {

			if (roleDto.getAuthorities().isEmpty()) {
				throw new Exception("Authorities cannot be empty");
			}
			Set<Authority> authorities = authorityService.getAuthorityByIds(roleDto.getAuthorities());

			role.setAuthority(authorities);
		}

		// name
		if (roleDto.getName() == null) {

			role.setName(currentRole.getName());
		} else {

			if (roleDto.getName().isBlank()) {
				throw new Exception("The value of name is not valid");
			}

			role.setName(roleDto.getName());
		}

		role.setUpdatedBy(user);
		role.setOrganization(user.getOrganization());
		logger.info("Role to persist ==========> " + role);
		try {
			roleRepository.save(role);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Role error ==========> id: " + ex.getMessage());
				throw new Exception("This role already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}

	}

	public void deleteRole(HttpServletRequest request, Long id) throws Exception {
		User user = contextService.getCurrentUser(request);

		Optional<Role> role = roleRepository.findByIdAndOrganizationId(id, user.getOrganization().getId());

		logger.info("To delete role ==========> id: " + id);
		if (role.isPresent()) {
			roleRepository.delete(role.get());
		} else {
			throw new Exception("The role does not exists.");
		}

	}

	public RoleDto getRole(HttpServletRequest request, Long id) throws Exception {
		User user = contextService.getCurrentUser(request);

		Optional<Role> role = roleRepository.findByIdAndOrganizationId(id, user.getOrganization().getId());

		logger.info("To get role ==========> id: " + id);
		RoleDto roleDto = new RoleDto();

		if (role.isPresent()) {
			roleDto.setName(role.get().getName());
			roleDto.setRoleId(role.get().getId());
			return roleDto;
		} else {
			throw new Exception("The role does not exists.");
		}

	}
}
