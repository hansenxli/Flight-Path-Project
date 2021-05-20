/** 
Author: Hansen Li
Date: Nov 28, 2019
*/

// Note: Dijkstra Algorithm base code used from GeekforGeeks resource sample code and modified for my class files

import java.io.*;
import java.util.*;

public class DijkstraAlgorithm {

	
	public int type1, type2;
	public String CostInTime[][], SVertex, DVertex;
	public List<String> listOfTheNodes;
	public Set<String> List;
	public List<StartRoot> ListOfVisitedNode;
	public HashMap<String, Integer> minimalDistance;
	public HashMap<String, Integer> distOfVertices;
	public PriorityQueue<Location> priorityQueue;

	public DijkstraAlgorithm(List<String> listOfTheNodes) {
		this.listOfTheNodes = listOfTheNodes;
		minimalDistance = new HashMap<String, Integer>();
		distOfVertices = new HashMap<String, Integer>();
		List = new HashSet<String>();
		ListOfVisitedNode = new ArrayList<StartRoot>();
		priorityQueue = new PriorityQueue<Location>(new Location());
	}

	private void locateAdjacent(String evaluationNode, StartRoot evaluationNodeList) {
		int d = -1;
		int newd = -1;

		for (int i = 0; i < CostInTime.length; i++) {
			if (!CostInTime[i][0].equals(evaluationNode))
				continue;
			String target;
			for (int j = 0; j < listOfTheNodes.size(); j++) {
				target = listOfTheNodes.get(j);
				if (!CostInTime[i][1].equals(target))
					continue;
				if (!List.contains(target)) {
					d = Integer.parseInt(CostInTime[i][type1]);
					newd = minimalDistance.get(evaluationNode) + d;
					if (newd < minimalDistance.get(target)) {
						minimalDistance.replace(target, newd);
						distOfVertices.replace(target,
								distOfVertices.get(evaluationNode) + Integer.parseInt(CostInTime[i][type2]));
						for (StartRoot path : ListOfVisitedNode) {
							if (path.exists(target))
								path.delete(target);
							break;
						}
						evaluationNodeList.add(target);
					}
					priorityQueue.add(new Location(target, minimalDistance.get(target)));
				}
			}
		}
	}

	private String getVetex() {
		String node = priorityQueue.remove().cityName;
		return node;
	}

	public void dijkastra(String costInTime[][], String requiredPath[]) {
		String vartexEvaluation;
		SVertex = requiredPath[0];
		DVertex = requiredPath[1];
		if (requiredPath[2].equalsIgnoreCase("C")) {
			type1 = 2;
			type2 = 3;
		} else {
			type1 = 3;
			type2 = 2;
		}

		this.CostInTime = costInTime;

		for (String vertex : listOfTheNodes) {
			minimalDistance.put(vertex, Integer.MAX_VALUE);
			distOfVertices.put(vertex, Integer.MAX_VALUE);
		}

		priorityQueue.add(new Location(SVertex, 0));
		minimalDistance.replace(SVertex, 0);
		distOfVertices.replace(SVertex, 0);
		while (!priorityQueue.isEmpty()) {
			vartexEvaluation = getVetex();
			StartRoot evaluationNodeList = new StartRoot();
			evaluationNodeList.setNode(vartexEvaluation);
			List.add(vartexEvaluation);
			locateAdjacent(vartexEvaluation, evaluationNodeList);
			if (!isThereVertex(ListOfVisitedNode, vartexEvaluation))
				ListOfVisitedNode.add(evaluationNodeList);
		}
	}

	private boolean isThereVertex(List<StartRoot> listOfVisitedVertex, String node) {
		for (StartRoot p : ListOfVisitedNode) {
			if (p.getCity().equals(node))
				return true;
		}
		return false;
	}

	public static List<String> PathofTheRoot(List<StartRoot> visitedCity, String target) {
		List<String> completePath = new ArrayList<String>();
		for (StartRoot path : visitedCity) {
			if (!path.exists(target))
				continue;
			completePath = PathofTheRoot(visitedCity, path.getCity());
			completePath.add(target);
			return completePath;
		}
		completePath.add(target);
		return completePath;
	}


}
