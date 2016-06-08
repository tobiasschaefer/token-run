package info.novatec.tr.delegate;

import javax.inject.Named;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.tr.websocket.ProcessInstanceController;

@Named
public class ServiceTaskEndListener implements ExecutionListener {

	@Autowired
	ProcessInstanceController controller;

	@Autowired
	ProcessEngine processEngine;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println(" Listened to task end for: " + execution.getProcessInstanceId() + ". "+findTaskId(execution.getActivityInstanceId()) + " name: " + execution.getCurrentActivityName());
		controller.serviceTaskEnded(execution.getProcessInstanceId(), findTaskId(execution.getActivityInstanceId()), findTaskName(execution.getActivityInstanceId()));
	}
	
	private String findTaskId(String activityInstanceId) {
		int delimiter = activityInstanceId.indexOf(":");
		String taskId = activityInstanceId.substring(delimiter+1);
		return taskId;
	}
	
	private String findTaskName(String activityInstanceId) {
		int delimiter = activityInstanceId.indexOf(":");
		String executionId = activityInstanceId.substring(0, delimiter);
		return executionId;
	}
}
