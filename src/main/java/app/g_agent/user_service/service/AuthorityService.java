package app.g_agent.user_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.model.Authority;
import app.g_agent.user_service.repository.AuthorityRepository;

@Service
public class AuthorityService {

	private static final Logger logger = LoggerFactory.getLogger(AuthorityService.class);

	AuthorityRepository authorityRepository;

	public AuthorityService(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	public Authority getAuthorityById(Long id) {
		Authority authority = authorityRepository.getReferenceById(id);
		return authority;
	}

	public Set<Authority> getAuthorityByIds(List<Long> ids) {
		ids.forEach(item -> logger.info("Authorities to fetch=====> " + item));
		Set<Authority> authorities = new HashSet<Authority>(authorityRepository.findAllById(ids));
		return authorities;
	}

	public Set<Authority> getAllAuthority() {
		logger.info("Fetching all Authorities");
		Set<Authority> authorities = new HashSet<Authority>(authorityRepository.findAll());
		return authorities;
	}
}
