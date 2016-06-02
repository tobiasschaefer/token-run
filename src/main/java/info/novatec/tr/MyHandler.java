package info.novatec.tr;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		System.out.println(message.toString());
		try {
			String messageText = "{ \"processId\":\"42\" ,"
			    + "\"status\": \"started\"}";
			TextMessage response = new TextMessage(messageText.getBytes());
			session.sendMessage(response);
			Thread.sleep(2000);
			messageText = "{ \"processId\":\"42\" ,"
			    + "\"status\": \"stoped\"}";
			response = new TextMessage(messageText.getBytes());
			session.sendMessage(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
