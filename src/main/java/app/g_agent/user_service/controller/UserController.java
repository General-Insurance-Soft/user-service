package app.g_agent.user_service.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@GetMapping("/get-list")
	public ResponseEntity<?> getUser(HttpServletRequest request) {

		Message message = new Message();
		try {
			return ResponseEntity.ok(userService.getUser(request));
		} catch (Exception ex) {
			message.setNameString("Error");
			message.setMessageString(ex.getMessage());
			return ResponseEntity.status(403).body(message);

		}

	}

	@GetMapping("/get-contacts-by-ids")
	public ResponseEntity<?> getUserByIds(HttpServletRequest request,
			@RequestHeader MultiValueMap<String, String> headers,
			@RequestParam String ids) {
		try {
			logger.info("Fetching all ids of size {} ========> " + ids);
			List<Long> idList = Arrays.stream(ids.split(","))
					.map(String::trim)
					.map(Long::parseLong)
					.toList();
			if (idList.size() > 100) {
				throw new Exception("You can only fetch 100 users at a time");
			}
			List<UserDto> users = userService.getUsersByIds(request, headers, idList);
			return ResponseEntity.ok(users);
		} catch (Exception ex) {
			Message message = new Message();
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
