package info.novatec.tr.delegate;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named
public class SendMessageDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("Level5_Message")
				.processInstanceId(execution.getProcessInstanceId()).correlate();
	}
}
