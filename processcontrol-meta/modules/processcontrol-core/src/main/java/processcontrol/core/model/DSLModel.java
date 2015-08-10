package processcontrol.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processcontrol.core.interpreter.ProcessVariable;
import processcontrol.core.json.model.BPMNModel;
import processcontrol.core.json.model.LinkDataArray;
import processcontrol.core.json.model.NodeDataArray;

public class DSLModel {

	private Node start;

	private Map<Integer, Node> nodeMap;

	private Map<Integer, List<Link>> linkMap;
	
	private List<Node> parallelGatewayList = new ArrayList<Node>();
	
	public DSLModel(BPMNModel bpmnModel) {
		nodeMap = new HashMap<Integer, Node>();
		linkMap = new HashMap<Integer, List<Link>>();
		createNodeMap(bpmnModel.getNodeDataArray());
		createLinkMap(bpmnModel.getLinkDataArray());
		//TODO Exception Handling!
		if(parallelGatewayList.size()>0)
			throw new RuntimeException("Parallel Gateway Error!");
	}

	public Node getStart() {
		return this.start;
	}
	
	public Map<Long, Map<String, Node>> findParallelNodePairs(){
		Map<Long, Map<String, Node>> parallelNodePairs = new HashMap<Long, Map<String, Node>>();
		for (Node node : nodeMap.values()) {
			if(node.getType().equals(ActionType.PARALLEL_GATEWAY)){
				String key = node.getGatewayName().split(":")[1];
				if(parallelNodePairs.containsKey(node.getParallelKey())){
					Map<String, Node> pnp = parallelNodePairs.get(node.getParallelKey());
					pnp.put(key, node);
					parallelNodePairs.put(node.getParallelKey(), pnp);
				}else{
					Map<String, Node> pnp = new HashMap<String, Node>();
					pnp.put(key, node);
					parallelNodePairs.put(node.getParallelKey(), pnp);
				}
			}
		}
		return parallelNodePairs;
	}

	public List<Node> getNextNodes(Node node, Map<String, ProcessVariable> processVariables) {
		List<Node> nodeList = new ArrayList<Node>();
		if (linkMap.containsKey(node.getKey())) {
			List<Link> linkList = linkMap.get(node.getKey());
			for (Link link : linkList) {
				if (link.getConditionName() != null) {
					if (processVariables.containsKey(link.getConditionName())) {
						if (link.getCondition() == (boolean) (processVariables.get(link.getConditionName()).getValue())) {
							nodeList.add(nodeMap.get(link.getTo()));
						}
					} else {
						throw new RuntimeException("No such process variable in Process! Conditionname: " + link.getConditionName());
					}
				} else {
					nodeList.add(nodeMap.get(link.getTo()));
				}
			}
		}else{
			return null;
		}
		return nodeList;
	}

	private void createLinkMap(List<LinkDataArray> linkDataArrayList) {
		for (LinkDataArray linkDataArray : linkDataArrayList) {
			Link link = new Link();
			link.setFrom(linkDataArray.getFrom());
			link.setTo(linkDataArray.getTo());
			link.setConditionName(null);
			if (linkDataArray.getText() != null) {
				if (linkDataArray.getText().length() > 0) {
					String[] condition = linkDataArray.getText().split(":");
					link.setConditionName(condition[0]);
					if(condition[1].equalsIgnoreCase("true")){
						link.setCondition(true);
					}else{
						link.setCondition(false);
					}
				}
			}
			addLinkToLinkMap(link);
		}
	}

	private void addLinkToLinkMap(Link link) {
		List<Link> linkList;
		if (linkMap.containsKey(link.getFrom())) {
			linkList = linkMap.get(link.getFrom());
		} else {
			linkList = new ArrayList<Link>();
		}
		linkList.add(link);
		linkMap.put(link.getFrom(), linkList);
	}

	private void createNodeMap(List<NodeDataArray> nodeDataArrayList) {
		for (NodeDataArray nodeDataArray : nodeDataArrayList) {
			Node node = new Node();
			int key = nodeDataArray.getKey();
			node.setKey(key);
			String category = nodeDataArray.getCategory();
			node.setCategory(category);
			node.setItem(nodeDataArray.getItem());
			node.setType(ActionType.getActionType(nodeDataArray));
			if(node.getType().equals(ActionType.PARALLEL_GATEWAY)){
				node.setGatewayName(nodeDataArray.getText());
				connectParallelGateways(node);
			}
			if (node.getType().equals(ActionType.TASK))
				node.setCommand(nodeDataArray.getText());
			if (node.getType().equals(ActionType.START))
				start = node;
			nodeMap.put(key, node);
		}
	}

	private void connectParallelGateways(Node node) {
		boolean sameGateway = false;
		Node sameGatewayNode = null;
		if(parallelGatewayList.size() != 0){
			for (Node gatewayNode : parallelGatewayList) {
				String gatewayNodeName = gatewayNode.getGatewayName().split(":")[0];
				String nodeName = node.getGatewayName().split(":")[0];
				if(gatewayNodeName.equalsIgnoreCase(nodeName)){
					node.setParallelKey(gatewayNode.getParallelKey());
					sameGatewayNode = gatewayNode;
					sameGateway = true;
				}
			}
			if(!sameGateway){
				addNodeToParallelGatewayList(node);
			}else{
				parallelGatewayList.remove(sameGatewayNode);
			}
		}else{
			addNodeToParallelGatewayList(node);
		}
	}
	
	private void addNodeToParallelGatewayList(Node node){
		node.setParallelKey(System.currentTimeMillis());
		parallelGatewayList.add(node);
	}
}
