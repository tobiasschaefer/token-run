package info.novatec.tr;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/rest")
public class CamundaRestResourceConfig extends ResourceConfig {


  @PostConstruct
  public void registerCamundaRestResources() {

    this.registerClasses(CamundaRestResources.getResourceClasses());
    this.registerClasses(CamundaRestResources.getConfigurationClasses());
    this.register(JacksonFeature.class);

  }

}


