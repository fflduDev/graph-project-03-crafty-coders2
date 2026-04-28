
package graph_template;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class ListBasedDiGraph implements DiGraph {
	private List<GraphNode> nodeList = new ArrayList<>();

	@Override
	public Boolean addNode(GraphNode node) {
		
		nodeList.add(node);
		return true;
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		if (!nodeList.contains(node)) {
			return false;
		}
		//List<GraphNode> neighbors = node.getNeighbors();
		for (GraphNode current : nodeList) {
			current.removeNeighbor(node);
		}
		
		nodeList.remove(node);
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		if (!nodeList.contains(node)) {
			return false;
		}
		node.setValue(newNodeValue);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		if (!nodeList.contains(node)) {
			return null;
		}
		// TODO Auto-generated method stub
		return node.getValue();
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {

		//BAD
		fromNode.addNeighbor(toNode, weight);
		
		//GOOD
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
	 	 
		targetFromNode.addNeighbor(targetToNode, weight);
	
		return true;
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		if (!nodeList.contains(fromNode) || !nodeList.contains(toNode)) {
			return false;
		}
		// TODO Auto-generated method stub
		return fromNode.removeNeighbor(toNode);
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		if (!nodeList.contains(fromNode) || !nodeList.contains(toNode)) {
			return false;
		}
		// TODO Auto-generated method stub
		if (fromNode.getDistanceToNeighbor(toNode) == null) {
			return false;
		}
		fromNode.removeNeighbor(toNode);
		return fromNode.addNeighbor(toNode, newWeight);
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		if (!nodeList.contains(fromNode) || !nodeList.contains(toNode)) {
			return null;
		}
		// TODO Auto-generated method stub
		return fromNode.getDistanceToNeighbor(toNode);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		if (!nodeList.contains(node)) {
			return null;
		}
		// TODO Auto-generated method stub
		return node.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		if (!nodeList.contains(fromNode) || !nodeList.contains(toNode)) {
			return false;
		}
		for (GraphNode neighbor : fromNode.getNeighbors()) {
			if (neighbor.getValue().equals(toNode.getValue())) {
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

		@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {//dan 
		// TODO Auto-generated method stub
		GraphNode start = getNode(fromNode.getValue()); 
		GraphNode target = getNode(toNode.getValue()); 

		List<GraphNode> visited = new ArrayList<>(); 
		List<GraphNode> queue = new ArrayList<>(); 

		queue.add(start); 
		visited.add(start); 

		while (!queue.isEmpty()) { 
			GraphNode current = queue.remove(0); 

			if (current.getValue().equals(target.getValue())) { 
				return true; 
			}

			for (GraphNode neighbor : current.getNeighbors()) { 
				if (!visited.contains(neighbor)) { 
					visited.add(neighbor); 
					queue.add(neighbor); 
				}
			}
		}

		return false; 
	}


	@Override
	public Boolean hasCycles() {//daN
		// TODO Auto-generated method stub
		List<GraphNode> visited = new ArrayList<>(); 
		List<GraphNode> stack = new ArrayList<>(); 

		for (GraphNode node : nodeList) { 
			if (dfsCycle(node, visited, stack)) { 
				return true; 
			}
		}
		return false; 
	}

	private Boolean dfsCycle(GraphNode node, List<GraphNode> visited, List<GraphNode> stack) { 
		if (stack.contains(node)) return true; 
		if (visited.contains(node)) return false; 

		visited.add(node); 
		stack.add(node); 

		for (GraphNode neighbor : node.getNeighbors()) {
			if (dfsCycle(neighbor, visited, stack)) return true; 
		}

		stack.remove(node); 
		return false; 
	}


	@Override
	public List<GraphNode> getNodes() {
		return nodeList;
	}

	@Override
	 
	public GraphNode getNode(String nodeValue) {
		for (GraphNode thisNode : nodeList) {
			if (thisNode.getValue().equals(nodeValue))
				return thisNode;
		}
		return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {//dan
		// TODO Auto-generated method stub
		GraphNode start = getNode(fromNode.getValue()); 
		GraphNode target = getNode(toNode.getValue()); 
		
		if (start == null || target == null) {
			return -1;
		}
		
		List<GraphNode> queue = new ArrayList<>(); 
		HashMap<GraphNode, Integer> distance = new HashMap<>(); 

		queue.add(start); 
		distance.put(start, 0); 

		while (!queue.isEmpty()) { 
			GraphNode current = queue.remove(0); 

			if (current.getValue().equals(target.getValue())) { 
				return distance.get(current);}

			for (GraphNode neighbor : current.getNeighbors()) { 
				if (!distance.containsKey(neighbor)) { 
					distance.put(neighbor, distance.get(current) + 1); 
					queue.add(neighbor); }}}
		return -1;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue());
		GraphNode target = getNode(toNode.getValue());
		
		
		// TODO Auto-generated method stub
		return 0;
	}

 
	 
	
}
