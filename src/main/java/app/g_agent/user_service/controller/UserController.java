package app.g_agent.user_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.g_agent.user_service.dto.UserDto;
import app.g_agent.user_service.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@GetMapping("/index")
	public Map<String, String> index() {

		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("results", "logged in successfully");
		return testMap;

	}

	@PostMapping("/create-user")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
		logger.info("User DTO from requerst " + userDto.toString());
		userService.createUser(userDto);
		return ResponseEntity.ok("User created successfully");
	}
}
