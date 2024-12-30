package app.g_agent.user_service.controller;

import java.util.HashMap;
import java.util.Map;

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
import app.g_agent.user_service.dto.UserDto;
import app.g_agent.user_service.service.UserService;
import app.g_agent.user_service.system.commons.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(HttpServletRequest request, @Valid @RequestBody UserDto userDto) {
		logger.info("User DTO from requerst " + userDto.toString());
		Message message = new Message();
		try {
			userService.createUser(request, userDto);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("User created successfully");
		return ResponseEntity.ok(message);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(HttpServletRequest request, @RequestParam Long user) {

		Message message = new Message();
		try {
			userService.deleteUser(request, user);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("User deleted successfully");
		return ResponseEntity.ok(message);
	}

	@GetMapping("/get")
	public ResponseEntity<?> getUser(HttpServletRequest request, @RequestParam Long user) {

		Message message = new Message();
		try {
			UserDto userDto = userService.getUser(request, user);
			return ResponseEntity.ok(userDto);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody UserDto userDto,
			@RequestParam Long user) {
		logger.info("User DTO from requerst " + userDto.toString());
		Message message = new Message();
		try {
			userService.updateUser(request, userDto, user);
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}
		message.setNameString("Success");
		message.setMessageString("User updated successfully");
		return ResponseEntity.ok(message);
	}
}
