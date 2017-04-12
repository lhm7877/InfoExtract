package com.HooMin;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

import addition.Computelli;
import addition.Node2;
import addition.Operator;
import addition.Ref;
import ke.Graph2;
public class InfoExtractWithKE2 {
 
	public InfoExtractWithKE2() {
	}

	public static void infoExtract(Ref aRef, CRFmodel aCRFmodel) {
		// 새로운 클래스, 함수를 만들어 내는 함수
		// evolution: 지식을 받아들여 스스로를 변형함
		System.out.print("java -cp mallet.... 지금 함수infoExtract 실행 중");
	}

	public void f() {
		System.out.print("지금 f함수 실행 중");
	}

	public static void main(String[] args) {

		// 일반적인 프로그래밍 방
		Ref aRef = new Ref();
//		CRFmodel aCRFmodel = new CRFmodel();

		aRef.setText("박성희, (2016), \"KE\", 정보관리학회, 33, (3), pp. 22-40 ");
//		aRef.type = "Ref"; // aRef의 타입이 "Ref"임을 안다면

		// InfoExtractWithKE2.infoExtract(aRef, aCRFmodel);

		// KE를 이용한 방법
		// 1. 위의 함수호출을 문제 그래프 형태로 바꾼다.
		// 2. 그래프를 traverse하면서 진행한다.

		// Computelli.compute("infoExtract", 1, 2);

		Graph2 graph = new Graph2();

		graph.addNode2(0, "input", 1.0,false);
		graph.addNode2(1, "infoExtract", 1.0,true);
		graph.addNode2(2, "output", 1.0,false);
		// 1번 방법
		// graph.addTriple(0, "infoExtract", 1.0, graph.Node2List.get(0),
		// graph.Node2List.get(1), graph.Node2List.get(2));

		// 2번 방법
		graph.addEdge2(0, "in", 1.0, (Node2)graph.Node2List.get(0), (Node2)graph.Node2List.get(1));
		graph.addEdge2(1, "out", 1.0, (Node2)graph.Node2List.get(1), (Node2)graph.Node2List.get(2));

		// graph2.addEdge2(0, "0번", 1.0, Node2List.get(0), Node2List.get(1));
		// graph2.addEdge2(1, "1번", 1.0, Node2List.get(1), Node2List.get(2));
		// graph2.addEdge2(2, "2번", 1.0, Node2List.get(0), Node2List.get(3));
		// graph2.addEdge2(3, "3번", 1.0, Node2List.get(2), Node2List.get(3));
		// graph2.addEdge2(4, "4번", 1.0, Node2List.get(2), Node2List.get(5));
		// graph2.addEdge2(5, "5번", 1.0, Node2List.get(3), Node2List.get(4));
		// graph2.addEdge2(6, "6번", 1.0, Node2List.get(4), Node2List.get(5));
		// graph2.addEdge2(7, "7번", 1.0, Node2List.get(5), Node2List.get(6));
		// graph2.addEdge2(8, "8번", 1.0, Node2List.get(5), Node2List.get(7));
		// graph2.addEdge2(8, "9번", 1.0, Node2List.get(6), Node2List.get(7));

		for (int i = 0; i < graph.Node2List.size(); i++) {
			System.out.print(((Node2)graph.Node2List.get(i)).value + " 와 연결된 노드 : ");

			for (int j = 0; j < ((Node2)graph.Node2List.get(i)).outEdge2.size(); j++) {
				if (!((Node2)graph.Node2List.get(i)).outEdge2.isEmpty())
					System.out.print(((Node2)graph.Node2List.get(i)).outEdge2.get(j).to_Node2.value + " ");
			}
			// for (int j = 0; j < Node2List.get(i).inEdge2.size(); j++) {
			// if (!Node2List.get(i).inEdge2.isEmpty())
			// System.out.print(Node2List.get(i).inEdge2.get(j).from_Node2.value
			// + " ");
			// }
			System.out.println();
		}

		Node2 fromNode2 = (Node2)graph.Node2List.get(0);
		Node2 endNode2 = (Node2)graph.Node2List.get(2);

		graph.open = new PriorityQueue<Node2>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Node2 c1 = (Node2) o1;
				Node2 c2 = (Node2) o2;

				return c1.fScore < c2.fScore ? -1 : c1.fScore > c2.fScore ? 1 : 0;
			}
		});

		// Graph2.pathFindingAStar(graph2, fromNode2, endNode2);
		//
		// System.out.print(endNode2.value);

		Node2 temp = endNode2.parent;
		// while (temp != null) {
		// System.out.print(" -> " + temp.value);
		// temp = temp.parent;
		// }
		//
		// graph2.init();
		//
		//// open.removeAll(open);
		// open.clear();
		InfoExtractWithKE2 infoExtractWithKE2 = new InfoExtractWithKE2();
		
		Graph2.pathFindingAStar(aRef,graph, fromNode2, endNode2,infoExtractWithKE2.makeAlgoGraph(),infoExtractWithKE2.makeKGraph());
		// System.out.print(endNode2.value);

		// Graph2.pathFindingAStarWithEdge(graph, endNode2, fromNode2);
		// System.out.print(fromNode2.value);
		Stack nodeStack = new Stack<Node2>();
		temp = endNode2;
		
		while(temp!=null){//거꾸로 꺼내기 위해 스택에 쌓음
			nodeStack.push(temp);
			temp=temp.parent;
		}
		Node2 testNode = (Node2) nodeStack.peek();
		
		
		
		pathFindingAstarResult(nodeStack,aRef);
	}
	
	public static void pathFindingAstarResult(Stack nodeStack, Ref aRef){
		Stack<Object> stack = new Stack<Object>();
		double a = 0, b = 0, c = 0;
		while(!nodeStack.isEmpty()){
			Node2 temp = (Node2) nodeStack.pop();
			System.out.print(" -> " + temp.value);
			if (temp.value.equals("input")) { // 여기서 edge를 'in'이라하고 'in'함수를 실행해서 해당
											// 변수에 값을 할당하는 작업을 하도록 해야 한다.
											// 즉,set함수를 실행하도록 하는 셈이다.
				// computelli에서 그래프를 만든다.temp와temp의 다음 노드를 넘겨준다.
				// propertyOf에서 infoExtract_Styled를 만든다.
				
//				Node finalNode = kGraphMaker.travelNode(kGraphMaker.findNode(aRef.type));
//				System.out.println("마지막 노드"+finalNode.value);
				System.out.println(temp.outEdge2.get(0).to_Node2.value);
				// stack.push("3.0");
				// stack.push("5.0");
			 	temp.setOutput(aRef.getText());
			} else if (temp.value.equals("infoExtract")) {
				System.out.println("************infoExtract************");
				Operator operator = new Operator(temp.inEdge2, temp.outEdge2, temp);
				c = Computelli.compute(temp.value, operator.getInputStack(),operator.getOutputStack());
			} else if (temp.value == "output") {
				System.out.println(a + temp.inEdge2.get(0).edge_Node2.value + b + " 는 " + c);

			}
			System.out.println(temp.value+"temp값****************");
			if (temp.value.equals("ref")){
				System.out.println("ref이라면");
			}
		}
	}
	public Graph2 makeAlgoGraph(){
		AlgoGraphMaker algoGraphMaker = new AlgoGraphMaker();
		return algoGraphMaker.init();
	}
	
	public Graph2 makeKGraph(){
		KGraphMaker kGraphMaker = new KGraphMaker();
		return kGraphMaker.init();		// 지식그래프를 만든다.
	}
}
//class CRFmodel {
//}