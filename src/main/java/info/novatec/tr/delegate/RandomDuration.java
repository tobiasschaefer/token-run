package info.novatec.tr.delegate;

import java.util.Random;

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
		long millis = (long) new Random().nextInt(10000);
		System.out.println("Sleeping "+millis+" milliseconds.");
		Thread.sleep(millis);
	}
}
