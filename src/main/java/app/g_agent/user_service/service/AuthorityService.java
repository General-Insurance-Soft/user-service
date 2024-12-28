package app.g_agent.user_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.g_agent.user_service.model.Authority;
import app.g_agent.user_service.repository.AuthorityRepository;

@Service
public class AuthorityService {

	AuthorityRepository authorityRepository;

	public AuthorityService(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	public Authority getAuthorityById(Long id) {
		Authority authority = authorityRepository.getReferenceById(id);
		return authority;
	}

	public List<Authority> getAuthorityByIds(List<Long> ids) {
		List<Authority> authorities = authorityRepository.findAllById(ids);
		return authorities;
	}
}
