package app.g_agent.user_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.dto.OrganizationDto;
import app.g_agent.user_service.model.Organization;
import app.g_agent.user_service.repository.OrganizationRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class OrganizationService {
	private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

	OrganizationRepository oganizationRepository;
	private ContextService contextService;

	public OrganizationService(OrganizationRepository oganizationRepository, ContextService contextService) {
		this.oganizationRepository = oganizationRepository;
		this.contextService = contextService;
	}

	public Organization getOrganizationById(Long id) {
		Organization organization = oganizationRepository.getReferenceById(id);
		return organization;
	}

	public void createOrganization(HttpServletRequest request, OrganizationDto organizationDto) throws Exception {

		Organization organization = new Organization();
		organization.setEmail(organizationDto.getEmail());
		organization.setLogo(organizationDto.getLogo());
		organization.setName(organizationDto.getName());
		organization.setUpdatedBy(contextService.getCurrentUser(request));
		organization.setPhone(organizationDto.getPhone());
		organization.setWebsite(organizationDto.getWebsite());

		logger.info("organization to persist ==========> " + organization.toString());

		try {
			oganizationRepository.save(organization);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				throw new Exception("This organization or user already exists. Can't create new organization");
			}
			throw ex; // Rethrow if not related to constraint violation
		}

	}

	public void createOrganization(Organization organization) throws Exception {

		logger.info("organization to persist ==========> " + organization.toString());

		try {
			oganizationRepository.save(organization);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				throw new Exception("This organization or user already exists. Can't create new organization");
			}
			throw ex; // Rethrow if not related to constraint violation
		}

	}
}
