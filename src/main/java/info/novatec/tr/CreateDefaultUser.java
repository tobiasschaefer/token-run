package info.novatec.tr;

import javax.annotation.PostConstruct;

import org.camunda.bpm.admin.impl.web.SetupResource;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.rest.dto.identity.UserCredentialsDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;





@Component
public class CreateDefaultUser {

	@Autowired
	IdentityService identityService;





	@PostConstruct
	public void ensureInitialUserIsCreated() {

		if (identityService.createUserQuery().userId("demo").count() > 0)
			return;

		User demo = identityService.newUser("demo");
		demo.setFirstName("John");
		demo.setLastName("Rambo");

		UserDto fromUser = UserDto.fromUser(demo, true);

		UserCredentialsDto credentials = new UserCredentialsDto();
		credentials.setPassword("demo");
		fromUser.setCredentials(credentials);

		new SetupResource().createInitialUser("default", fromUser);
	}
}
