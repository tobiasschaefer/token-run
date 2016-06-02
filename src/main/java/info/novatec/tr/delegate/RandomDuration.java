package info.novatec.tr.delegate;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Random duration between 0 and 10 seconds.
 */
@Named
public class RandomDuration implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Thread.sleep((long)Math.random()*10000);
	}
}
