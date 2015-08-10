package processcontrol.core.interpreter;

import java.util.List;
import java.util.Map;

import processcontrol.core.model.ActionType;
import processcontrol.core.model.DSLModel;
import processcontrol.core.model.Node;

public class SubProcessBean implements Runnable {

	private long processKey;

	private Node start;

	// private List<SubProcessBean> subProcesses;

	private DSLModel dslModel;

	private Map<String, ProcessVariable> processVariables;

	public SubProcessBean(long processKey, DSLModel dslModel, Node start, Map<String, ProcessVariable> processVariables) {
		this.processKey = processKey;
		this.processVariables = processVariables;
		this.dslModel = dslModel;
		this.start = start;
	}

	@Override
	public void run() {
		tryTaskCommand(start);
		handleNode(dslModel.getNextNodes(start, processVariables));
	}

	private void tryTaskCommand(Node node) {
		if (ActionType.TASK.equals(node.getType())) {
			System.out.println("Command: " + node.getCommand() + " / Thread: " + Thread.currentThread().getName());
		}
	}

	private void handleNode(List<Node> nodeList) {
		if (nodeList != null) {
			if (nodeList.size() > 1) {
				// for (Node node : nodeList) {
				//
				// }
			} else {
				Node node = nodeList.get(0);
				tryTaskCommand(node);
				if (node.getParallelKey() != processKey) {
					List<Node> newNodeList = dslModel.getNextNodes(node, processVariables);
					if (newNodeList != null)
						handleNode(newNodeList);
				}
			}
		}
	}

	public long getProcessKey() {
		return processKey;
	}

	public void setProcessKey(long processKey) {
		this.processKey = processKey;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public DSLModel getDslModel() {
		return dslModel;
	}

	public void setDslModel(DSLModel dslModel) {
		this.dslModel = dslModel;
	}

	public Map<String, ProcessVariable> getProcessVariables() {
		return processVariables;
	}

	public void setProcessVariables(Map<String, ProcessVariable> processVariables) {
		this.processVariables = processVariables;
	}
}
