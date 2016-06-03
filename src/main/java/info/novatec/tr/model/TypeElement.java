package info.novatec.tr.model;

public class TypeElement {
	private final String name;
	private final String id;

	public TypeElement(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}
}
