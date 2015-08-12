package processcontrol.core.interpreter;

import java.util.List;
import java.util.Map;

import processcontrol.core.model.ActionType;
import processcontrol.core.model.DSLModel;
import processcontrol.core.model.Node;

public class SubProcessBean implements Runnable {

	private TaskInterpreter taskInterpreter;
	
	private long processKey;

	private Node start;

	private Map<Long, List<SubProcessBean>> subProcesses;

	private DSLModel dslModel;

	private Map<String, ProcessVariable> processVariables;
	
	private Map<Long, Map<String, Node>> parallelNodePairs;

	public SubProcessBean(long processKey, DSLModel dslModel, Node start, Map<String, ProcessVariable> processVariables, Map<Long, Map<String, Node>> parallelNodePairs, TaskInterpreter taskInterpreter) {
		this.processKey = processKey;
		this.processVariables = processVariables;
		this.dslModel = dslModel;
		this.start = start;
		this.subProcesses = null;
		this.parallelNodePairs = parallelNodePairs;
		this.taskInterpreter = taskInterpreter;
	}
	
	public void setSubProcesses(Map<Long, List<SubProcessBean>> subProcesses){
		this.subProcesses = subProcesses;
	}

	@Override
	public void run() {
		tryTaskCommand(start);
		handleNode(dslModel.getNextNodes(start, processVariables));
	}

	private void tryTaskCommand(Node node) {
		if (ActionType.TASK.equals(node.getType())) {
			taskInterpreter.taskCommand(node.getCommand());
//			System.out.println("Command: " + node.getCommand() + " / Thread: " + Thread.currentThread().getName());
		}
	}

	private void handleNode(List<Node> nodeList) {
		for (Node node : nodeList) {
			tryTaskCommand(node);
			if (ActionType.PARALLEL_GATEWAY.equals(node.getType())) {
				if (node.getParallelKey() != processKey) {
					List<SubProcessBean> subProcessList = subProcesses.get(node.getParallelKey());
					Thread[] threads = new Thread[subProcessList.size()];
					for (int i = 0; i < threads.length; i++) {
						threads[i] = new Thread(subProcessList.get(i));
						threads[i].start();
					}
					for (int i = 0; i < threads.length; i++) {
						try {
							threads[i].join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					List<Node> newNodeList = dslModel.getNextNodes(parallelNodePairs.get(node.getParallelKey()).get("end"),
							processVariables);
					if (newNodeList != null)
						handleNode(newNodeList);
				}
			} else if(ActionType.END.equals(node.getType())){
				//TODO Rework end message
				System.out.println("Process Ended!");
			} else {
				List<Node> newNodeList = dslModel.getNextNodes(node, processVariables);
				if (newNodeList != null)
					handleNode(newNodeList);
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
