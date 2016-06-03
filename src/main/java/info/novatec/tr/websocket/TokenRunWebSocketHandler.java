package info.novatec.tr.websocket;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TokenRunWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	ProcessEngine processEngine;
	
	@Autowired
	ProcessInstanceController processInstanceController;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		System.out.println(message.getPayload());
		String key = (String)message.getPayload().subSequence(1, message.getPayload().length()-1);
		ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(key);
//		ProcessInstanceController.getInstance().processInstanceStarted(session, pi.getProcessInstanceId());
		System.out.println("Session: "+session.getRemoteAddress());
		processInstanceController.processInstanceStarted(session, pi.getProcessInstanceId());
	}

}
