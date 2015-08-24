package processcontrol.core.interpreter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import processcontrol.core.json.model.BPMNModel;
import processcontrol.core.model.DSLModel;
import processcontrol.core.model.Node;

@Service
public class ProcessBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private DSLModel dslModel;

	private Map<String, ProcessVariable> processVariables;

	private Map<Long, List<SubProcessBean>> subProcesses;

	private Map<Long, Map<String, Node>> parallelNodePairs;
	
	@Resource
	private ApplicationContext applicationContext;
	
	public void setProcessBean(BPMNModel bpmnModel, Map<String, ProcessVariable> processVariables){
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
					subProcessList.add(new SubProcessBean(key, dslModel, node, processVariables, parallelNodePairs, applicationContext));
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
	
	public void start(){
		Node start = dslModel.getStart();
		SubProcessBean mainProcess = new SubProcessBean(System.currentTimeMillis(), dslModel, start, processVariables, parallelNodePairs, applicationContext);
		mainProcess.setSubProcesses(subProcesses);
		Thread t = new Thread(mainProcess);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
