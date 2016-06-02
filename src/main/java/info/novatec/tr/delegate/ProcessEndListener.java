package info.novatec.tr.delegate;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.tr.websocket.ProcessInstanceController;

@Named
public class ProcessEndListener implements ExecutionListener {

	@Autowired
	ProcessInstanceController controller;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("Listened to piid: " + execution.getProcessInstanceId());
		controller.processInstanceCompleted(execution.getProcessInstanceId());
	}
}
