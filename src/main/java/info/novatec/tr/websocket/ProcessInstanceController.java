package info.novatec.tr.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ProcessInstanceController {
	
	private Map<String, WebSocketSession> sessions = new HashMap<>();
	
	@Autowired
	ProcessEngine processEngine;
	
	public void processInstanceStarted(WebSocketSession session, String piid) {
		System.out.println("Process instance started. (piid="+piid+")");
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
		System.out.println("Instance "+piid+" completed. "+processEngine);
		HistoricProcessInstance hpi = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(piid).singleResult();
		long endTime = System.currentTimeMillis();
		long duration = endTime - hpi.getStartTime().getTime();
		WebSocketSession session = sessions.get(piid);
		String messageText = "{ \"processId\":\""+piid+"\" ,"
			    + "\"status\": \"completed\","
	    		+ "\"duration\": \""+formatDuration(duration)+"\"}";
		TextMessage message = new TextMessage(messageText.getBytes());
		try {
			session.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Event processInstanceCompleted in "+formatDuration(duration)+" sent. (piid=+"+piid+", WS-Session="+session.getRemoteAddress()+")");
		sessions.remove(piid);
	}
	
	public void humanTaskCreated(String piid, String tid, String taskName) {
		System.out.println("Human Task entered. (piid="+piid+", tid="+tid+")");
		WebSocketSession session = sessions.get(piid);
		System.out.println(session);
		String messageText = "{ \"taskId\":\""+tid+"\" ,"
			    + "\"status\": \"entered\", "
				+ "\"taskName\": \""+taskName+"\"}";
		TextMessage message = new TextMessage(messageText.getBytes());
		try {
			session.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Event humanTaskEntered sent. (piid=+"+piid+", WS-Session="+session.getRemoteAddress()+")");
	}
	
	private String formatDuration(long duration) {
		return String.format("%d min, %d sec, %d millis", 
			    TimeUnit.MILLISECONDS.toMinutes(duration),
			    TimeUnit.MILLISECONDS.toSeconds(duration),
			    TimeUnit.MILLISECONDS.toMillis(duration-TimeUnit.MILLISECONDS.toSeconds(duration)*1000) 
			);

	}

}
