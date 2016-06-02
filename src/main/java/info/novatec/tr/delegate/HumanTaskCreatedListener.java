package info.novatec.tr.delegate;

import javax.inject.Named;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.tr.websocket.ProcessInstanceController;

@Named
public class HumanTaskCreatedListener implements TaskListener {

	@Autowired
	ProcessInstanceController controller;

	@Autowired
	ProcessEngine processEngine;
	

	@Override
	public void notify(DelegateTask task) {
		System.out.println(" Listened to task startet for: " + task.getId() + ". "+task.getTaskDefinitionKey());
		controller.humanTaskCreated(task.getProcessInstanceId(), task.getId(), task.getTaskDefinitionKey());
	}
}
