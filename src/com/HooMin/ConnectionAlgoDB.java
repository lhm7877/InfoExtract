package com.HooMin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


public class ConnectionAlgoDB {
	static String dbUrl = "jdbc:mysql://127.0.0.1:3306/elab?characterEncoding=UTF-8";
	static String dbUsername = "librarian";
	static String dbPassword = "40211";
	PriorityQueue<Node> pqforCandidateAlgo;
	static boolean updateDB = false;

	public ConnectionAlgoDB() {

	}

	public static boolean existRelevantAlgorithm(String queryOperator) {

		boolean output = false;

		Statement statement = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();

			String str = "select * from Algorithm where Name='" + queryOperator
					+ "';";
			rs = statement.executeQuery(str);

			System.out.println("query in existRelevantAlgorithm:" + queryOperator);
			

			String className = "";
			String source = "";
			int count = 0;
			
			try {
				rs.last(); count = rs.getRow(); rs.beforeFirst();
			} catch(Exception e) {
				System.err.println(e.getMessage()+"ARGH!!");
			}

			while (rs.next()) {
				String id = rs.getString("ID");
				String name = rs.getString("Name");
				className = rs.getString("ClassName");
				String inArgType = rs.getString("InArgType");
				String outArgType = rs.getString("OutArgType");
				source = rs.getString("Source");
				System.out.println(id + "\t" + name + "\t" + className + "\t"
						+ inArgType + "\t" + outArgType + "\t" + source);
				count++;
			}

			if (count != 0) {
				output = true;
			}

		} catch (SQLException e) {
			System.err.print(e.getMessage() + " ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " FUUUUUUUUUU!");
		}
		return output;

	}
	public static ResultSet getRdfs(){
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
			String str = "select * from rdfs";
			rs = statement.executeQuery(str);
			rs.beforeFirst();
		} catch (SQLException e) {
			System.err.print(e.getMessage() + "SQLException ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + "Exception FUUUUUUUUUU!");
		}
		return rs;
	}
	public static ResultSet getAlgorithm(){
		Statement statement = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
			String str = "select * from algorithm";
			rs = statement.executeQuery(str);
		} catch (SQLException e) {
			System.err.print(e.getMessage() + "SQLException ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + "Exception FUUUUUUUUUU!");
		}
		return rs;
	}
	
	

	public static ResultSet getRelevantAlgorithm(String queryOperator) {
		Statement statement = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
//			String str = "select count(*) from Algorithm where Name='"
//					+ queryOperator + "';";

//			rs = statement.executeQuery(str);


			String str = "select * from Algorithm where Name='" + queryOperator + "';";
			rs = statement.executeQuery(str);

			System.out.println("query:" + queryOperator);
			
			String className = "";
			String source = "";

			int count = 0;
			try {
			    rs.last();
			    count = rs.getRow();
			    rs.beforeFirst();
			}
			catch(Exception ex) {
				System.err.println(ex.getMessage()+"ARGH!!");
			}
			
//			while (rs.next()) {
//				String id = rs.getString("ID");
//				String name = rs.getString("Name");
//				className = rs.getString("ClassName");
//				String inArgType = rs.getString("InArgType");
//				String outArgType = rs.getString("OutArgType");
//				source = rs.getString("Source");
//				System.out.println(id + "\t" + name + "\t" + className + "\t"
//						+ inArgType + "\t" + outArgType + "\t" + source);
//			}
			rs.beforeFirst();
			
			if (count != 0) {
				return rs;
			} else
				return null;

		} catch (SQLException e) {
			System.err.print(e.getMessage() + " ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " FUUUUUUUUUU!");
		}
		return rs;
	}
	
	public static void updateDB(String table, String name, String className,String inArgType,
			String outArgType, String source, int numInArg, String operSymbol, String methodName){
		System.out.println("updateDB 실행");
		Statement statement = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO `elab`.`"+table+"` (`Name`, `ClassName`, `InArgType`, `OutArgType`, `Source`, `NumInArg`, `OperSymbol`, `MethodName`) VALUES ('"+name+"', '"+className+"', '"+inArgType+"', '"+outArgType+"', '"+source+"', '"+numInArg+"', '"+operSymbol+"', '"+methodName+"');");
			updateDB = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getCandidateAlgorithms(String queryOperator) {

		Statement statement = null;
		ResultSet rs = null, tempRS = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
			// String str =
			// "select * from Algorithm where Name='"+queryOperator+"';";

			String str = "select B.className, B.Source "
					+ " from elab.Ontology as A , elab.Algorithm as B "
					+ "where (select A.name where (A.BT in (SELECT A.Name  FROM elab.Ontology as A where A.NT ='"
					+ queryOperator + "'))) = B.className;";

			rs = statement.executeQuery(str);
			


			String className = "";
			String source = "";
			int count = 0;
			try {
				rs.last(); count = rs.getRow(); rs.beforeFirst();
				
			} catch (Exception e)
			{
				System.err.println(e.getMessage()+"ARGH!!");
			}
			System.out.println("query: " + queryOperator +"\nNumOf Result Set: " + count);


			 while (rs.next()) { //String id = rs.getString("className");
			 //String name = rs.getString("Name"); 
			 className = rs.getString("ClassName"); //String inArgType =
			 //rs.getString("InArgType"); //String outArgType =
			 //rs.getString("OutArgType"); 
			 source = rs.getString("Source");
			 //System.out.println(id + "\t" + name + "\t" + className + 
			 //"\t" + inArgType + "\t" + outArgType + // "\t" + source);
			  System.out.println(className + "\t" + source ); }
			 if (count!=0) {rs.beforeFirst();return rs;} else return null;
			 
		} catch (SQLException e) {
			System.err.print(e.getMessage() + " ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " FUUUUUUUUUU!");
		}
		return tempRS;

	}

	private static double measureHowDiffAlgorithmIsFromConstraint(String className,
			double inArg1, double inArg2, double outArg, String source) {
		double output = 0.0;
		File helloWorldJava = new File("./" + className + ".java");// ("./add.java");
		System.out.println("here 5-2 in calculateCostOfAlgorithm");
		if (helloWorldJava.getParentFile().exists()
				|| helloWorldJava.getParentFile().mkdirs()) {

			try {
				Writer writer = null;
				try {
					writer = new FileWriter(helloWorldJava);
					writer.write(source);
					writer.flush();
				} finally {
					try {
						writer.close();
					} catch (Exception e) {
					}
				}

				/** Compilation Requirements *********************************************************************************************/
				DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
				StandardJavaFileManager fileManager = compiler
						.getStandardFileManager(diagnostics, null, null);

				// This sets up the class path that the compiler will use.
				// I've added the .jar file that contains the DoStuff interface
				// within in it...
				List<String> optionList = new ArrayList<String>();
				optionList.add("-classpath");
				optionList.add(System.getProperty("java.class.path")
						+ ";dist/InlineCompiler.jar");

				Iterable<? extends JavaFileObject> compilationUnit = fileManager
						.getJavaFileObjectsFromFiles(Arrays
								.asList(helloWorldJava));
				JavaCompiler.CompilationTask task = compiler.getTask(null,
						fileManager, diagnostics, optionList, null,
						compilationUnit);
				/********************************************************************************************* Compilation Requirements **/
				if (task.call()) {
					/** Load and execute *************************************************************************************************/
					System.out.println("Yipe");
					// Create a new custom class loader, pointing to the
					// directory that contains the compiled
					// classes, this should point to the top of the package
					// structure!
					URLClassLoader classLoader = new URLClassLoader(
							
							new URL[] { new File("./").toURI().toURL() });
					System.out.println("Yipe2");
					// Load the class from the classloader by name....
					Class<?> loadedClass = classLoader.loadClass(className);// "add"
					System.out.println("Yipe3");
					// Create a new instance...
					Object obj = loadedClass.newInstance();
					classLoader.close();
					System.out.println("Yipe4");
					// Santity check
					if (obj instanceof DoStuff) {
						// Cast to the DoStuff interface
						DoStuff stuffToDo = (DoStuff) obj;
						// Run it baby
						stuffToDo.doStuff();
					}
					/************************************************************************************************* Load and execute **/
				} else {
					System.out.println("Error");
					for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
							.getDiagnostics()) {
						System.out.format("Error on line %d in %s%n",
								diagnostic.getLineNumber(), diagnostic
										.getSource().toUri());
					}
				}
				fileManager.close();
				System.out.println("here is 6");
			} catch (IOException | ClassNotFoundException
					| InstantiationException | IllegalAccessException exp) {
				exp.printStackTrace();
			}
		}
		String strString = "java.lang.Double";// "java.lang.String";
		String strClass = className; // "addition";//"tutorial.HTSCRE";
		String strMethod = className;// "add";//getNodes(infmodel, a ,p);
		try {
			System.out.println("here is 7");
			System.out.println("strClass :"+strClass);

			URLClassLoader classLoader = new URLClassLoader(
					new URL[] { new File("./").toURI().toURL() });

			Class<?> passedClass = classLoader.loadClass(strClass);// "add"  tutorial.HTSCRE

			
			// parameter types for methods
			Class<?>[] partypes = new Class[] { Class.forName(strString),
					Class.forName(strString) }; // input type
			System.out.println("here is 8");

			// Create method object. method name and parameter types
			Method meth = passedClass.getMethod(strMethod, partypes); // class,
																		// method,
																		// input
																		// type
			System.out.println("here is 9");

			// parameter types for constructor
			Class<?>[] constrpartypes = new Class[] {};
			// Create constructor object. parameter types
			Constructor<?> constr = passedClass.getConstructor(constrpartypes); // class
																				// constructor
			// create instance
			Object dummyto = constr.newInstance(); // create class instance

			// Arguments to be passed into method
			Object[] arglist = new Object[] { (inArg1), (inArg2) };
			// invoke method!!
			System.out.println("here is 10");
			output = (Double) meth.invoke(dummyto, arglist); // method, class
																// instance
			classLoader.close();
			
			System.out.println("here is 11");
			System.out.println(arglist[0] + " " + className + " " + arglist[1]
					+ " = " + output);

			System.out.println("here is 12");

			return Math.abs((double) (output - outArg));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
	}
		
		return Math.abs((double) (output - outArg));

	}

	static class Node {
		int id;
		double cost; // error cost with respect to each ground truth set elements
		String name;// node name
		String source;
	}

	static class PriorityQueueAlgorithm implements Comparator<Node> {
		public int compare(Node one, Node two) {
			return (int) (one.cost) - (int) (two.cost);
		}
	}

	public static PriorityQueue<Node> getEvaluationResult(String queryOperator,
			ResultSet rs1) {

		String className = "";
		String source = "";

		/* 횒�닞횆횕청�� 횕쨉첬횕횥책횓�횗횕철짤횕첫횗 횒�닞횆횕횩횆횓채챤 Node횓�◈� 횕�닞챈횕챦횗 횓쨋짢횑횗짜횑챦첬횓찾짠 */
		/* A* 횕챦책횒�돟�졗렺뗏㉲뤒뗐꼴뤓뮨� 횒쨉짢횑챵횗횑챦첬횓찾짠 */
		System.out.println("1 in find function ");

		PriorityQueueAlgorithm pqa = new PriorityQueueAlgorithm();
		System.out.println("2 in find function ");

		PriorityQueue<Node> pq = new PriorityQueue<Node>(10, pqa);
		System.out.println("3 in find function ");

		try {

			System.out.println("4 in find function ");

			if (rs1 == null)
				System.out.println("rs1 is null");
			else
				System.out.println("rs1 is not null");

			while (rs1.next()) {

				System.out.println("5 in find function ");

				// String id = rs1.getString("className");
				// String name = rs1.getString("Name");
				className = rs1.getString("ClassName");
				// String inArgType = rs1.getString("InArgType");
				// String outArgType = rs1.getString("OutArgType");
				source = rs1.getString("Source");
				// System.out.println(id + "\t" + name + "\t" + className +
				// "\t" + inArgType + "\t" + outArgType +
				// "\t" + source);
				System.out.println(className + "\t" + source);

				int count = 0;

				String operatorName = "";
				double sumOfCost = 0.0;
				ResultSet rs = ConnectionGTBase
						.getRelevantGroundTruth(queryOperator);
				while (rs.next()) {
					int id = rs.getInt("idKnowledges");
					operatorName = rs.getString("Operator");
					double inArg1 = Double
							.parseDouble(rs.getString("Operand1"));
					double inArg2 = Double
							.parseDouble(rs.getString("Operand2"));
					double outArg = Double.parseDouble(rs.getString("Output"));
					System.out.println(id + "\t" + className + "\t" + inArg1
							+ "\t" + inArg2 + "\t" + outArg);
					count++;
					/*
					 * 횑챦챵횓횉챵횕첫챵 횑천횗횓�돟짜 횕챦책횒�돟�졗렺뗏㉲뤒뗐꼴뤓냉� 횓책횆횑챦챵횕처짢 횑챦짜횓찾�
					 * 횕횩횆횕찾첫횕첫짜 횕챰쨘횓횩횪횓횉챵 횓횩청횓채챤횕횩횆횓�◈� 횑척챦횕첫�닆횑챦첬횓찾짠
					 */
					sumOfCost += measureHowDiffAlgorithmIsFromConstraint(className, inArg1,
							inArg2, outArg, source);
					System.out.println("sumOfCost: " + sumOfCost);

				}
				double cost = (sumOfCost / count);
				Node algoNode = new Node();
				algoNode.cost = cost;
				algoNode.name = className;
				algoNode.source = source;
				System.out.println("name: " + className + "\tcost: " + cost
						+ "\tsource: " + source);
				pq.offer(algoNode);
			}
		} catch (SQLException e) {
			System.err.print(e.getMessage() + " ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " FUUUUUUUUUU!");
		}
		return pq;

	}

	public static boolean linkOperatorWithAlgorithm(String queryOperator,
			String algorithmName) {
		boolean output = false;

		Statement statement = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
			String str = "UPDATE Algorithm SET Name='" + queryOperator
					+ "'  WHERE Algorithm.ClassName = '" + algorithmName + "';";

			statement.executeUpdate(str);

			str = "select * from Algorithm where Name='" + queryOperator + "';";

			ResultSet rs = statement.executeQuery(str);

			System.out.println("query:" + queryOperator);

			String className = "";
			String source = "";
			int count = 0;

			while (rs.next()) {
				String id = rs.getString("ID");
				String name = rs.getString("Name");
				className = rs.getString("ClassName");
				String inArgType = rs.getString("InArgType");
				String outArgType = rs.getString("OutArgType");
				source = rs.getString("Source");
				System.out.println(id + "\t" + name + "\t" + className + "\t"
						+ inArgType + "\t" + outArgType + "\t" + source);
				count++;
			}

			// source 횓�◈� 횓�닞천횕챦횗횕척챤횕첬쨘횓짤짜 횒�닊�닆횒�돞횋횕처챗 횑챤쨘횕처�닞횕횉�닞횕청챗횓�◈� 횓횗짙횕챰짜횕횗첬
			// 횒�닞챠횕첫횗 횒쨉짢횑챦첬횓찾짠 .
			// 횕쨋창, 횕청횜횓�졻�▣뤓뮨� 횑챤쨘횕처�닞횕횉�닞횕청챗횕첫짜횒�돟�� 횒�돞�닞횒�돟쨘횒�닞척횕첫횆 횕처�닞횕횉�닞
			// 횒�돞�닞횒�돟쨘횕첫짜횓찾짠.
			// source횕첫챵Java 횑책책횕찾짹횕첫짜 횑챦횗횕철챤횑챦챵횓찾짠.

			// operating()
			System.out.println("here 1");
			// ParseTreeTest aParseTree = new ParseTreeTest();

			System.out.println("here 2");
			// parsetree_test.main();
			// FileInputStream in = new
			// FileInputStream("/Users/sunghee/Documents/sunghee-data/2015programs/workspace-java/rdf-prog/src/InfoExtractWithKE.java");

			System.out.println("here 2-1");
			if (count != 0)
				output = true;
			else
				output = false;
		} catch (SQLException e) {
			System.err.print(e.getMessage() + " ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " FUUUUUUUUUU!");
		}

		return output;
	}

	/* calculate the accuracy of each algorithm against ground truth. */

	public boolean evaluate(String queryOperator, double a1, double a2) {
//		ConnectionGTBase kbase = new ConnectionGTBase();
		ResultSet rs = null;

		// Apparently speaking, the term knowledge should be replaced with ground truth or training set 
		// by measurement method.
		// see if there is ground truth related to the query operator
		if (ConnectionGTBase.existRelevantGroundTruth(queryOperator) == true) {
			rs = ConnectionGTBase.getRelevantGroundTruth(queryOperator);
		}

		if (rs == null)
		{
			System.out.println("rs: is null on existRelevantKnowledge in evaluate...");
			return false;
		}
		else
		{
			System.out.println("rs is not null on existRelevantKnowledge in evaluate");
		}
		
		/* get candidate algorithms related to the operator */

		ResultSet candidateAlgorithmSet = getCandidateAlgorithms(queryOperator);
		if (candidateAlgorithmSet == null) {
			System.out.println("candidateAlgorithmSet: is null on getCandidateAlgorithms in evaluate");
			return false;
		}
		else
		{
			System.out.println("candidateAlgorithmSet is not null on getCandidateAlgorithms in evaluate");
		}
		/*
		 * 횑천횗횓�돟짜횕횩횆횕찾첫 횑챦챵횓횉챵횑챦챵횓횉챵횕처챗 횒�닞횆횕�졻닆횕챵짰 횕횩횆횕찾첫(횕�졗�횓찾쨉)횕첫횗 횓책횆횕청횜횑챦짜횕횗첬
		 * 횕�졗끖뤴�졗졗뚀�첬 횕챦책횒�돟�졗렺뗏㉲뤒뗐꼴뤓뮨� 횑횋챗횕횋창횑챦첬횓찾짠
		 */
		System.out.println("getEvaluationResult ...");
		PriorityQueue<Node> pqforCandidateAlgo = getEvaluationResult(queryOperator, candidateAlgorithmSet);
		System.out.println("getEvaluationResult complete");

		/*
		 * 횒�닞횆횕청�� 횕�졗끖뤴�졗졗뚀�첬 횕챦책횒�돟�졗렺뗏㉲뤒뗐꼴뤓뮨� 횕횩횆횕찾첫횕첫챵 횕처�닞횕횉�닞횕청챗횕척횆
		 * 횕처�닞횒�돞�닞횕찾첬횑횉짰횓찾짠.
		 */
		System.out.println("linking operator with algorithm ...");
		System.out.println("queryOperator: " + queryOperator);
		System.out.println("pq.peek().name: " + pqforCandidateAlgo);
		while (pqforCandidateAlgo.peek().cost == 0) { // 횓�횗횕철짤횕첫짜 0횕첫짜횓첫쨘횓채챤 횕째�닞횒짹짜횓횩책
										// 횒�닞횆횕횩횆횒�돟�� 횑책챗횓찾짰횑챦챵횓채챤 횒�돞횋횕첫횆 횓횗횪횓짢짜
										// 횓찾짰횕횪첬횑챦짰. 횓횉챵횕짠챘횕처챗 횒�돟�졗뤡��� 횑챦횗횕철챤
										// 횕청횪횕첫책.
			
			System.out.println("pq. " + pqforCandidateAlgo);
			linkOperatorWithAlgorithm(queryOperator, pqforCandidateAlgo.peek().name);
			pqforCandidateAlgo.poll();
			System.out.println("1.pq.size(): " + pqforCandidateAlgo.size());
			System.out.println("operator has been linked with algorithm complete");
//			return true;
		}
		System.out.println("2.pq.size(): " + pqforCandidateAlgo.size());
		
		if (pqforCandidateAlgo.peek().cost != 0)
			{return false;}
		else {return true;}
	}

	public static interface DoStuff {

		public void doStuff();
	}

}
