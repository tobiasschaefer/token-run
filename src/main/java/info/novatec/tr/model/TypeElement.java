package info.novatec.tr.model;

public class TypeElement {
	private final String type;
	private final String id;
	private final String name;

	public TypeElement(String type, String id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}


	public String getType() {
		return type;
	}


	public String getName() {
		return name;
	}
}
