package app.g_agent.user_service.system.commons;

import org.springframework.stereotype.Service;

@Service
public class Message {

	private String nameString;
	private String messageString;

	public String getNameString() {
		return nameString;
	}

	public void setNameString(String nameString) {
		this.nameString = nameString;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

}
