package processcontrol.core.interpreter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import processcontrol.core.akka.interpreter.MainAkkaInterpreter;
import processcontrol.core.json.model.BPMNModel;
import processcontrol.core.model.DSLModel;
import processcontrol.core.model.Node;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;

public class ProcessBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private DSLModel dslModel;

	private Map<String, ProcessVariable> processVariables;

	private Map<String, List<SubProcessBean>> subProcesses;

	private Map<String, Map<String, Node>> parallelNodePairs;

	private ActorSystem system = ActorSystem.create("CreationSystem", ConfigFactory.load("remotecreation"));

	private ActorRef commandPrinter = system.actorOf(Props.create(MainAkkaInterpreter.class), "MainAkkaInterpreter");

	public ProcessBean() {
	}

	public ProcessBean(BPMNModel bpmnModel, Map<String, ProcessVariable> processVariables) {
		dslModel = new DSLModel(bpmnModel);
		this.processVariables = processVariables;
		this.parallelNodePairs = dslModel.findParallelNodePairs();
		generateSubProcesses(parallelNodePairs);
	}

	private void generateSubProcesses(Map<String, Map<String, Node>> parallelNodePairs) {
		if (!parallelNodePairs.isEmpty()) {
			subProcesses = new HashMap<String, List<SubProcessBean>>();
			for (String key : parallelNodePairs.keySet()) {
				Map<String, Node> specificSubProcess = parallelNodePairs.get(key);
				List<SubProcessBean> subProcessList = new ArrayList<SubProcessBean>();
				for (Node node : dslModel.getNextNodes(specificSubProcess.get("start"), processVariables)) {
					subProcessList.add(new SubProcessBean(key, dslModel, node, specificSubProcess.get("end"), processVariables, parallelNodePairs,
							commandPrinter));
				}
				subProcesses.put(key, subProcessList);
			}
		}
		for (String key : subProcesses.keySet()) {
			List<SubProcessBean> sbList = subProcesses.get(key);
			for (SubProcessBean sb : sbList) {
				sb.setSubProcesses(subProcesses);
			}
		}
	}

	public void start() {
		final Node start = dslModel.getStart();
		final Node end = dslModel.getEnd();
		SubProcessBean mainProcess = new SubProcessBean(UUID.randomUUID().toString(), dslModel, start, end, processVariables, parallelNodePairs, commandPrinter);
		mainProcess.setSubProcesses(subProcesses);
		mainProcess.start();
		try {
			mainProcess.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		system.terminate();
	}
}
