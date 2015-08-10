package processcontrol.core.model;

import processcontrol.core.json.model.NodeDataArray;

public enum ActionType {
	TASK("activity", 0, "task"), START("event", 1, "start"), END("event", 1, "end"), PARALLEL_GATEWAY("gateway", 1, "and"), EXCLUSIVE_GATEWAY(
			"gateway", 4, "xor");

	private String category;

	private Integer type;

	private String name;

	private ActionType(String category, Integer type, String name) {
		this.category = category;
		this.type = type;
		this.name = name;
	}

	public static ActionType getActionType(NodeDataArray nodeDataArray) {
		for (ActionType actionType : ActionType.values()) {
			if ("event".equals(nodeDataArray.getCategory())) {
				if (nodeDataArray.getItem().equalsIgnoreCase(actionType.name))
					return actionType;
			} else if (nodeDataArray.getCategory().equalsIgnoreCase(actionType.category)
					&& (nodeDataArray.getTaskType() == (actionType.type) || nodeDataArray.getGatewayType() == (actionType.type))) {
				return actionType;
			}
		}
		return null;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
