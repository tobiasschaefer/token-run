package info.novatec.tr.websocket.messages;

public class ProcessInstanceCompleted {
	
	private String id;
	private String duration;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
}
