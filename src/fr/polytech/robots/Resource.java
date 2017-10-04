package fr.polytech.robots;

public class Resource {

	private ResourceType type;

	public Resource(ResourceType type) {
		this.type = type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public ResourceType getType() {
		return this.type;
	}

}