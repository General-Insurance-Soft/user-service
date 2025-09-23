package app.g_agent.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.g_agent.user_service.dto.OrganizationDto;
import app.g_agent.user_service.dto.RoleDto;
import app.g_agent.user_service.model.User;
import app.g_agent.user_service.service.OrganizationService;
import app.g_agent.user_service.service.RoleService;
import app.g_agent.user_service.service.UserService;
import app.g_agent.user_service.system.commons.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/organization")
@Validated
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	OrganizationService organizationService;

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public ResponseEntity<?> createOrganization(HttpServletRequest request,
			@Valid @RequestBody OrganizationDto organizationDto) {
		logger.info("Role DTO from requerst " + organizationDto.toString());
		Message message = new Message();
		try {
			organizationService.createOrganization(request, organizationDto);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("Organization created successfully");
		return ResponseEntity.ok(message);
	}

	@GetMapping("/get-org-id")
	public Long getOrganizationId(HttpServletRequest request) {
		try {
			return userService.getUserOrg(request);
		} catch (Exception ex) {
			logger.error("Error fetching organization ID: " + ex.getMessage());
			return null;
		}
	}

}
