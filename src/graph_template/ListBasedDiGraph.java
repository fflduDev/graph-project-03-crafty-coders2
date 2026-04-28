
package graph_template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class ListBasedDiGraph implements DiGraph {
	private List<GraphNode> nodeList = new ArrayList<>();

	@Override
	public Boolean addNode(GraphNode node) {
		
		nodeList.add(node);
		return true;
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		GraphNode start = getNode(node.getValue()); 
		if (start == null) {
			return false;
		}
		//List<GraphNode> neighbors = node.getNeighbors();
		for (GraphNode current : nodeList) {
			current.removeNeighbor(start);
		}
		
		nodeList.remove(start);
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		GraphNode start = getNode(node.getValue()); 
		if (start == null) {
			return false;
		}
		start.setValue(newNodeValue);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		GraphNode start = getNode(node.getValue()); 
		if (start == null) {
			return null;
		}
		// TODO Auto-generated method stub
		return start.getValue();
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {

		//BAD
		//fromNode.addNeighbor(toNode, weight);
		
		//GOOD
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
	 	
		if (targetFromNode == null || targetToNode == null) {
			return false;
		}
	
		return targetFromNode.addNeighbor(targetToNode, weight);
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue()); 
		GraphNode target = getNode(toNode.getValue()); 
		if (start == null || target == null) {
			return false;
		}
		// TODO Auto-generated method stub
		return start.removeNeighbor(target);
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		GraphNode start = getNode(fromNode.getValue()); 
		GraphNode target = getNode(toNode.getValue()); 
		if (start == null || target == null) {
			return false;
		}
		// TODO Auto-generated method stub
		if (start.getDistanceToNeighbor(target) == null) {
			return false;
		}
		start.removeNeighbor(target);
		return start.addNeighbor(target, newWeight);
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue()); 
		GraphNode target = getNode(toNode.getValue()); 
		if (start == null || target == null) {
			return null;
		}
		// TODO Auto-generated method stub
		return start.getDistanceToNeighbor(target);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		GraphNode start = getNode(node.getValue()); 
		if (start == null) {
			return null;
		}
		// TODO Auto-generated method stub
		return start.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue()); 
		GraphNode target = getNode(toNode.getValue()); 
		if (start == null || target == null) {
			return false;
		}
		for (GraphNode neighbor : start.getNeighbors()) {
			if (neighbor.getValue().equals(target.getValue())) {
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
		
		if (start == null || target == null) {
			return false;
		}

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
		
		if (start == null || target == null) {
			return -1;
		}
		
		List<GraphNode> visited = new ArrayList<>();
		Map<GraphNode, Integer> distance = new HashMap<>();
		
		for (GraphNode neighbors : nodeList) {
			distance.put(neighbors, Integer.MAX_VALUE);
			visited.add(neighbors);	
		}
		
		distance.put(start, 0);
		
		
		while (!visited.isEmpty()) {
			GraphNode current = null;
			
			for (GraphNode neighbor : visited) {
				if (current == null || distance.get(neighbor) < distance.get(current)) {
					current = neighbor;
				}
			}
			
			if (current.getValue().equals(target.getValue())) {
				return distance.get(current);
			}
			
			visited.remove(current);
			
			if (distance.get(current) == Integer.MAX_VALUE) {
				break;
			}
			
			for (GraphNode neighbor : current.getNeighbors()) {
				int dist = distance.get(current) + current.getDistanceToNeighbor(neighbor);
				
				if (dist < distance.get(neighbor)) {
					distance.put(neighbor, dist);
				}
				
			}
			
		}
		// TODO Auto-generated method stub
		return -1;
	}

 
	 
	
}
