package com.HooMin;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class KGraph<V, E> {
	static ArrayList<Node> nodeList;
	static ArrayList<Edge> edgeList;

	static PriorityQueue<Node> open;

	public KGraph() {
		// TODO Auto-generated constructor stub
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();

	}

	public void addNode(int id, String value, double hScore) {
		Node node = new Node(id, value, hScore);
		boolean isIn = true;
		for ( int i = 0; i< nodeList.size(); i++){
			if(nodeList.get(i).value.equals(value))
				isIn=false;
		}
		if(isIn){
			nodeList.add(node);
		}
	}

	public void addEdge(int id, String value, double weight, Node from_node, Node to_node) {
		Edge edge = new Edge(id, value, weight);
		edge.from_node = from_node;
		edge.to_node = to_node;
		from_node.outEdge.add(edge);
		to_node.inEdge.add(edge);
		edgeList.add(edge);
	}

	public static void pathFindingAStar(KGraph aGraph, Node fromNode, Node endNode) {
		open.add(fromNode);
		Node current;
		while (true) {
			current = open.poll();
			if (current == null)
				break;
			current.visited = true;

			if (current.equals(endNode)) {
				return;
			}

			Node t;
			for (int i = 0; i < current.outEdge.size(); i++) {
				t = current.outEdge.get(i).to_node;
				checkAndUpdateCost(current, t, current.fScore + current.outEdge.get(i).weight);
			}
			for (int i = 0; i < current.inEdge.size(); i++) {
				t = current.inEdge.get(i).from_node;
				checkAndUpdateCost(current, t, current.fScore + current.inEdge.get(i).weight);
			}

		}
	}

	static void checkAndUpdateCost(Node current, Node t, double cost) {
		if (t == null || t.visited)
			return;// t��尡 ���ų�. || t�� �湮������ �־��ٸ� ����
		double t_final_cost = t.hScore + cost;

		boolean inOpen = open.contains(t);
		if (!inOpen || t_final_cost < t.fScore) {// tĭ�� ���� openť�� ���� || t�� �޸���ƽ
													// + cost�� ���<t�� ������� �����
													// fscore
			t.fScore = t_final_cost;
			t.parent = current;
			if (!inOpen)
				open.add(t);
		}
	}
	public static void printGraph(){
		for(int i =0; i < nodeList.size(); i++){
			System.out.print(nodeList.get(i).value + " �� ����� ��� : ");
			for(int j = 0; j< nodeList.get(i).outEdge.size(); j++){
				if(!nodeList.get(i).outEdge.isEmpty())
				System.out.print(nodeList.get(i).outEdge.get(j).to_node.value + " ");
			}
			for(int j = 0; j<nodeList.get(i).inEdge.size(); j++){
				if(!nodeList.get(i).inEdge.isEmpty())
				System.out.print(nodeList.get(i).inEdge.get(j).from_node.value + " ");
			}
			System.out.println();
		}
	}
	@SuppressWarnings("unchecked")
	public static void init() {
		int nodesNu = 5;
		double[] tempHScore = { 10.0, 8.0, 5.0, 3.0, 7.0, 2.0, 6.0, 0.0 };
		KGraph graph = new KGraph<>();

		graph.addNode(0, "Line", 0);
		graph.addNode(1, "pp", 0);
		graph.addNode(2, "rp", 0);
		graph.addNode(3, "List", 0);
		graph.addNode(4, "List", 0);
		graph.addNode(5, "Public", 0);
		graph.addNode(6, "Public", 0);

		graph.addEdge(0, "Property", 1.0, nodeList.get(0), nodeList.get(1));
		graph.addEdge(1, "Property", 1.0, nodeList.get(0), nodeList.get(2));
		graph.addEdge(2, "Type", 1.0, nodeList.get(1), nodeList.get(3));
		graph.addEdge(3, "Type", 1.0, nodeList.get(2), nodeList.get(4));
		graph.addEdge(4, "Modifier", 1.0, nodeList.get(3), nodeList.get(5));
		graph.addEdge(5, "Modifier", 1.0, nodeList.get(4), nodeList.get(6));

	}
}

class Node {
	int id;
	String value;
	ArrayList<Edge> inEdge; // inbound
	ArrayList<Edge> outEdge; // outbound
	Node parent;
	boolean visited;
	double hScore = 0;
	double fScore = 0;

	Node(int id, String value, double hScore) {
		this.id = id;
		this.value = value;
		this.hScore = hScore;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();
		this.visited = false;
	}
}

class Edge {

	int id;
	Node from_node;
	Node to_node;
	String value;
	boolean visited = false;
	double weight;

	Edge(int id, String value, double weight) {
		this.id = id;
		this.value = value;
		this.weight = weight;
		this.visited = false;
	}
}