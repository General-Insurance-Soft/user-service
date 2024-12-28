package app.g_agent.user_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import app.g_agent.user_service.model.Organization;
import app.g_agent.user_service.repository.OrganizationRepository;

@Service
public class OrganizationService {

	OrganizationRepository oganizationRepository;

	public OrganizationService(OrganizationRepository oganizationRepository) {
		this.oganizationRepository = oganizationRepository;
	}

	public Organization getOrganizationById(Long id) {
		Organization organization = oganizationRepository.getReferenceById(id);
		return organization;
	}
}
