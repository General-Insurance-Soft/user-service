package app.g_agent.user_service.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(),
				"Invalid request. Please check your input.", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}

class ErrorDetails {
	private int status;
	private String message;
	private String path;

	public ErrorDetails(int status, String message, String path) {
		this.status = status;
		this.message = message;
		this.path = path;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
