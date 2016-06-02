package info.novatec.tr.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ProcessInstanceController {
	
	private Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();
	
	public void processInstanceStarted(WebSocketSession session, String piid) {
		sessions.put(piid, session);
		String messageText = "{ \"processId\":\""+piid+"\" ,"
			    + "\"status\": \"started\"}";
		TextMessage message = new TextMessage(messageText.getBytes());
		try {
			session.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Event processInstanceStarted sent. (piid=+"+piid+", WS-Session="+session.getRemoteAddress()+")");
	}

	public void processInstanceCompleted(String piid) {
		WebSocketSession session = sessions.get(piid);
		String messageText = "{ \"processId\":\""+piid+"\" ,"
			    + "\"status\": \"completed\"}";
		TextMessage message = new TextMessage(messageText.getBytes());
		try {
			session.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Event processInstanceCompleted sent. (piid=+"+piid+", WS-Session="+session.getRemoteAddress()+")");
		sessions.remove(piid);
	}

}
