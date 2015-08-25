package processcontrol.core.interpreter;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import processcontrol.core.model.ActionType;
import processcontrol.core.model.DSLModel;
import processcontrol.core.model.Node;
import akka.actor.ActorRef;

public class SubProcessBean extends Thread {

	private String processKey;

	private Node start;

	private Node end;

	private Map<String, List<SubProcessBean>> subProcesses;

	private DSLModel dslModel;

	private Map<String, ProcessVariable> processVariables;

	private Map<String, Map<String, Node>> parallelNodePairs;

	private ActorRef commandPrinter;

	public SubProcessBean(String processKey, DSLModel dslModel, Node start, Node end, Map<String, ProcessVariable> processVariables,
			Map<String, Map<String, Node>> parallelNodePairs, ActorRef commandPrinter) {
		this.processKey = processKey;
		this.processVariables = processVariables;
		this.dslModel = dslModel;
		this.start = start;
		this.subProcesses = null;
		this.parallelNodePairs = parallelNodePairs;
		this.commandPrinter = commandPrinter;
		this.end = end;
	}

	public void setSubProcesses(Map<String, List<SubProcessBean>> subProcesses) {
		this.subProcesses = subProcesses;
	}

	@Override
	public void run() {
		NodeRunner previousNodeRunner = new NodeRunner(start);
		previousNodeRunner.start();
		Node previousNode = start;
		Node actualNode = null;
		do {
			NodeRunner actualNodeRunner;
			List<Node> nodeList = dslModel.getNextNodes(previousNode, processVariables);
			if (nodeList.size() == 1) {
				actualNode = nodeList.get(0);
				if (ActionType.PARALLEL_GATEWAY.equals(actualNode.getType())) {
					if (actualNode.getParallelKey() != processKey) {
						List<SubProcessBean> subProcessList = subProcesses.get(actualNode.getParallelKey());
						List<Node> newNodeList = dslModel.getNextNodes(parallelNodePairs.get(actualNode.getParallelKey()).get("end"), processVariables);
						actualNode = newNodeList.get(0);
						actualNodeRunner = new NodeRunner(actualNode);
						actualNodeRunner.start(subProcessList, previousNodeRunner);
						previousNode = actualNode;
						previousNodeRunner = actualNodeRunner;
					}
				} else {
					actualNodeRunner = new NodeRunner(actualNode);
					actualNodeRunner.start(previousNodeRunner);
					previousNode = actualNode;
					previousNodeRunner = actualNodeRunner;
				}
			} else {
				// TODO List Validation
				System.err.println("Something went wrong!");
			}
		} while (!actualNode.getType().equals(end.getType()));
		this.interrupt();
	}

	private void tryTaskCommand(Node node) {
		if (ActionType.TASK.equals(node.getType())) {
			commandPrinter.tell(commandFormatter(node.getCommand(), DateTime.now().toString("hh:mm:ss:SSS"), Thread.currentThread().getName()), null);
		}
	}

	private String commandFormatter(String command, String time, String thread) {
		return "Time: " + time + " / Command: " + command + " / Thread: " + thread;
	}

	private class NodeRunner extends Thread {

		private Node node;

		public NodeRunner(Node node) {
			this.node = node;
		}

		public void start(NodeRunner nodeRunner) {
			try {
				nodeRunner.join();
			} catch (InterruptedException e) {
			}
			this.start();
		}

		public void start(List<SubProcessBean> subProcessList, NodeRunner nodeRunner) {
			try {
				nodeRunner.join();
			} catch (InterruptedException e) {
			}
			for (SubProcessBean subProcessBean : subProcessList) {
				subProcessBean.start();
			}
			try {
				for (SubProcessBean subProcessBean : subProcessList) {
					subProcessBean.join();
				}
			} catch (InterruptedException e) {
			}
			this.start();
		}

		@Override
		public void run() {
			tryTaskCommand(node);
		}
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
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
