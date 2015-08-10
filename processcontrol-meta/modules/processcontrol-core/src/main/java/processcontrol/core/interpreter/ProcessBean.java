package processcontrol.core.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processcontrol.core.json.model.BPMNModel;
import processcontrol.core.model.ActionType;
import processcontrol.core.model.DSLModel;
import processcontrol.core.model.Node;

public class ProcessBean implements Runnable {

	private DSLModel dslModel;

	private Map<String, ProcessVariable> processVariables;

	private Map<Long, List<SubProcessBean>> subProcesses;

	private Map<Long, Map<String, Node>> parallelNodePairs;

	public ProcessBean(BPMNModel bpmnModel, Map<String, ProcessVariable> processVariables) {
		dslModel = new DSLModel(bpmnModel);
		this.processVariables = processVariables;
		this.parallelNodePairs = dslModel.findParallelNodePairs();
		generateSubProcesses(parallelNodePairs);
	}

	private void generateSubProcesses(Map<Long, Map<String, Node>> parallelNodePairs) {
		if (!parallelNodePairs.isEmpty()) {
			subProcesses = new HashMap<Long, List<SubProcessBean>>();
			for (Long key : parallelNodePairs.keySet()) {
				Map<String, Node> specificSubProcess = parallelNodePairs.get(key);
				List<SubProcessBean> subProcessList = new ArrayList<SubProcessBean>();
				for (Node node : dslModel.getNextNodes(specificSubProcess.get("start"), processVariables)) {
					subProcessList.add(new SubProcessBean(key, dslModel, node, processVariables));
				}
				subProcesses.put(key, subProcessList);
			}
		}
	}

	@Override
	public void run() {
		Node start = dslModel.getStart();
		handleNode(dslModel.getNextNodes(start, processVariables));
	}

	private void tryTaskCommand(Node node) {
		if (ActionType.TASK.equals(node.getType())) {
			System.out.println("Command: " + node.getCommand() + " / Thread: " + Thread.currentThread().getName());
		}
	}
	
	private void handleNode(List<Node> nodeList) {
		for (Node node : nodeList) {
			tryTaskCommand(node);
			if (ActionType.PARALLEL_GATEWAY.equals(node.getType())) {
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
				List<Node> newNodeList = dslModel.getNextNodes(parallelNodePairs.get(node.getParallelKey()).get("end"), processVariables);
				if (newNodeList != null)
					handleNode(newNodeList);
			} else {
				List<Node> newNodeList = dslModel.getNextNodes(node, processVariables);
				if (newNodeList != null)
					handleNode(newNodeList);
			}
		}
	}

}
