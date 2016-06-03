package info.novatec.tr.model;

import java.util.LinkedList;
import java.util.List;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElementsOfType {

	@Autowired
	RepositoryService repoService;

	@RequestMapping(method={RequestMethod.GET}, path = {"{processDefinitionId}/elements"}, produces="application/json")
	@ResponseBody
	public List<TypeElement> logLevelScore(@PathVariable String processDefinitionId) {
		List<TypeElement> typeElements = new LinkedList<TypeElement>();

		BpmnModelInstance model = repoService.getBpmnModelInstance(processDefinitionId);

		for (ModelElementType typeElement : model.getModel().getTypes())
			for(ModelElementInstance elementInstance : model.getModelElementsByType(typeElement))
				addElement(typeElements, typeElement.getTypeName(), elementInstance.getAttributeValue("id"), elementInstance.getAttributeValue("name"));

		return typeElements;
	}

	private void addElement(List<TypeElement> typeElements, String typeName, String typeId, String name) {
		if(typeId == null)
			return;

		if(name == null)
			name = "";

		typeElements.add(new TypeElement(typeName, typeId, name));
	}

}
