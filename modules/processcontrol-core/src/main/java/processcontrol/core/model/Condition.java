package processcontrol.core.model;

public class Condition {
	
	private String conditionName;
	
	private String conditionExpression;
	
	private Integer conditionKey;

	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	public Integer getConditionKey() {
		return conditionKey;
	}

	public void setConditionKey(Integer conditionKey) {
		this.conditionKey = conditionKey;
	}

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	
}
