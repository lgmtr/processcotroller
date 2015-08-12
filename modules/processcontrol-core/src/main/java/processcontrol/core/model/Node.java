package processcontrol.core.model;

public class Node {
	
	private int key;
	
	private String item;
	
	private String category;
	
	private ActionType type;
	
	private String command;
	
	private String gatewayName;
	
	private long parallelKey = 0;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public long getParallelKey() {
		return parallelKey;
	}

	public void setParallelKey(long parallelKey) {
		this.parallelKey = parallelKey;
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

}
