package info.novatec.tr.delegate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Named;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
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
		System.out.println(" Listened to task startet for: " + task.getId());
		List<String> outputParameterNames = new ArrayList<String>();
		for (ModelElementInstance mei :  task.getBpmnModelElementInstance().getExtensionElements().getElements()) {
			if ("inputOutput".equals(mei.getElementType().getTypeName())) {
				for (DomElement de : mei.getDomElement().getChildElements()) {
					if ("outputParameter".equals(de.getLocalName())) {
						System.out.println(de.getAttribute("name"));	
						outputParameterNames.add(de.getAttribute("name"));
					}
				}
			}
		}
		controller.humanTaskCreated(task.getProcessInstanceId(), task.getId(), task.getTaskDefinitionKey(), outputParameterNames);
	}
}
