package app.g_agent.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.g_agent.user_service.dto.RoleDto;
import app.g_agent.user_service.service.RoleService;
import app.g_agent.user_service.system.commons.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/role")
@Validated
public class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	RoleService roleService;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(HttpServletRequest request, @Valid @RequestBody RoleDto roleDto) {
		logger.info("Role DTO from requerst " + roleDto.toString());
		Message message = new Message();
		try {
			roleService.createRole(request, roleDto);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("Role created successfully");
		return ResponseEntity.ok(message);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> createUser(HttpServletRequest request, @Valid @RequestBody RoleDto roleDto) {
		logger.info("Role DTO from requerst " + roleDto.toString());
		Message message = new Message();
		try {
			roleService.createRole(request, roleDto);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("Role created successfully");
		return ResponseEntity.ok(message);
	}
}
