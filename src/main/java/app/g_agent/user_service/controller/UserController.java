package app.g_agent.user_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@GetMapping("/index")
	public Map<String, String> index() {

		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("results", "logged in successfully");
		return testMap;

	}
}
