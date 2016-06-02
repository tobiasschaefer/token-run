package info.novatec.tr;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;





@Component
public class CreateDefaultUser {

	@Autowired
	IdentityService identityService;





	@PostConstruct
	public void createUser() {
		if (identityService.createUserQuery().userId("demo").count() > 0)
			return;

		User demo = identityService.newUser("demo");
		demo.setFirstName("John");
		demo.setLastName("Rambo");
		demo.setPassword("demo");

		identityService.saveUser(demo);
	}
}
