package processcontrol.core.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processcontrol.core.json.model.BPMNModel;
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
					subProcessList.add(new SubProcessBean(key, dslModel, node, processVariables, parallelNodePairs));
				}
				subProcesses.put(key, subProcessList);
			}
		}
		for (Long key : subProcesses.keySet()) {
			List<SubProcessBean> sbList = subProcesses.get(key);
			for (SubProcessBean sb : sbList) {
				sb.setSubProcesses(subProcesses);
			}
		}
	}

	@Override
	public void run() {
		Node start = dslModel.getStart();
		SubProcessBean mainProcess = new SubProcessBean(System.currentTimeMillis(), dslModel, start, processVariables, parallelNodePairs);
		mainProcess.setSubProcesses(subProcesses);
		Thread t = new Thread(mainProcess);
		t.start();
	}
}
