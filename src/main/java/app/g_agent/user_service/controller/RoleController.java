package app.g_agent.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<?> createRole(HttpServletRequest request, @Valid @RequestBody RoleDto roleDto) {
		logger.info("Role DTO from requerst =======>" + roleDto.toString());
		roleDto.getAuthorities().forEach(item -> logger.debug("Authorities passed from request=====> " + item));
		Message message = new Message();
		try {
			roleService.createRole(request, roleDto);
		} catch (Exception ex) {
			logger.info("Authorities to fetch=====> " + ex.getMessage());
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("Role created successfully");
		return ResponseEntity.ok(message);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteRole(HttpServletRequest request, @RequestParam Long role) {

		Message message = new Message();
		try {
			roleService.deleteRole(request, role);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("Role deleted successfully");
		return ResponseEntity.ok(message);
	}

	@GetMapping("/get")
	public ResponseEntity<?> getRole(HttpServletRequest request, @RequestParam Long role) {

		Message message = new Message();
		try {
			RoleDto roleDto = roleService.getRole(request, role);
			return ResponseEntity.ok(roleDto);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateRole(HttpServletRequest request, @Valid @RequestBody RoleDto roleDto,
			@RequestParam Long role) {
		logger.info("Role DTO from requerst " + roleDto.toString());
		Message message = new Message();
		try {
			roleService.updateRole(request, roleDto, role);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("Role updated successfully");
		return ResponseEntity.ok(message);
	}
}
