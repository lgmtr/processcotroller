package processcontrol.core.interpreter;

public class ProcessVariable {

	private Object value;
	
	private Class<?> classOfValue;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Class<?> getClassOfValue() {
		return classOfValue;
	}

	public void setClassOfValue(Class<?> classOfValue) {
		this.classOfValue = classOfValue;
	}
	
}
