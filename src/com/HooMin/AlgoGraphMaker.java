package com.HooMin;

import java.sql.ResultSet;
import java.sql.SQLException;

import ke.Graph2;

public class AlgoGraphMaker {
	Graph2 aGraph;
	ResultSet rs;

	// �׷��� �׽�Ʈ
	public static void main(String[] args) {
		AlgoGraphMaker algoGraphMaker = new AlgoGraphMaker();
		// aGraph.printGraph(aGraph);
	}

	public Graph2 init(Graph2 aGraph) {
		rs = ConnectionAlgoDB.getAlgorithm();
		try {
			while (rs.next()) {
				if (!(rs.getString("ClassName")==null || rs.getString("ClassName").equals(""))){
					aGraph.addNode2withInArgType(0, rs.getString("ClassName"), 0.0, true,rs.getString("InArgType"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aGraph;
	}
	
}