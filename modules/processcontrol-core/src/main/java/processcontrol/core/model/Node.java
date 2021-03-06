package processcontrol.core.model;

import java.util.List;

public class Node {

	private Integer key;

	private String item;

	private String category;

	private ActionType type;

	private String command;

	private String gatewayName;

	private List<Condition> gatewayCondition = null;

	private String parallelKey = "";

	private String prop1 = "";

	private String prop2 = "";
	
	private String prop3 = "";

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	public String getProp3() {
		return prop3;
	}

	public void setProp3(String prop3) {
		this.prop3 = prop3;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
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

	public String getParallelKey() {
		return parallelKey;
	}

	public void setParallelKey(String parallelKey) {
		this.parallelKey = parallelKey;
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public List<Condition> getGatewayCondition() {
		return gatewayCondition;
	}

	public void setGatewayCondition(List<Condition> gatewayCondition) {
		this.gatewayCondition = gatewayCondition;
	}

}
