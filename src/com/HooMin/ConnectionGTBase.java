package com.HooMin;



import java.sql.*;


public class ConnectionGTBase {

	public ConnectionGTBase() {

	}

	public static boolean existRelevantGroundTruth(String queryOperator) {
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/elab?characterEncoding=UTF-8";
		String dbUsername = "librarian";
		String dbPassword = "40211";
		boolean output = false;

		Statement statement = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
			String str = "select * from GroundTruth where Operator='"
					+ queryOperator + "';";

			rs = statement.executeQuery(str);
			int count = 0;
			
			try {
				rs.last();
				count = rs.getRow();
				rs.beforeFirst();
			} catch(Exception e) {
				count = 0;
			}

			System.out.println("query in GTBase:" + queryOperator);
			System.out.println("count in GTBase" + count);

			String className = "";

			 while (rs.next()) { int id = rs.getInt("idKnowledges"); 
			 className = rs.getString("Operator"); 
			 String inArg1 = rs.getString("Operand1"); 
			 String inArg2 = rs.getString("Operand2"); 
			 String outArg = rs.getString("Output");
			 System.out.println(id + "\t" + className + "\t" + inArg1 + "\t" +
			 inArg2 + "\t" + outArg ); count++; } 
			 
			 rs.beforeFirst();
			
			rs.close();
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

	public static ResultSet getRelevantGroundTruth(String queryOperator) {
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/elab?characterEncoding=UTF-8";
		String dbUsername = "librarian";
		String dbPassword = "40211";

		Statement statement = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
			String str = "select * from GroundTruth where Operator='"
					+ queryOperator + "';";

			rs = statement.executeQuery(str);
			int count;
			
			try {
				rs.last();
				count = rs.getRow();
				rs.beforeFirst();
			} catch(Exception e) {
				count = 0;
			}
			
			
			 String className="";;
			 
			 while (rs.next()) { int id = rs.getInt("idKnowledges"); className
			 = rs.getString("Operator"); String inArg1 =
			 rs.getString("Operand1"); String inArg2 =
			 rs.getString("Operand2"); String outArg = rs.getString("Output");
			 System.out.println(id + "\t" + className + "\t" + inArg1 + "\t" +
			 inArg2 + "\t" + outArg );} 
			 
			 rs.beforeFirst();
			 if (count!=0) {return rs;} 
			 else {return null;}
			 
		} catch (SQLException e) {
			System.err.print(e.getMessage() + " ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " FUUUUUUUUUU!");
		}

		return rs;

	}

	public static double connect(String queryOperator, double a1, double a2,
			double a3) {

		String dbUrl = "jdbc:mysql://127.0.0.1:3306/elab?characterEncoding=UTF-8";
		String dbUsername = "librarian";
		String dbPassword = "40211";
		Double output = null;

		Statement statement = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();
			String str = "select * from GroundTruth;";

			System.out.println("Here is 1");
			ResultSet rs = statement.executeQuery(str);
			System.out.println("Here is 2");
			int count = 0;
			System.out.println("Here is 3");

			while (rs.next()) {
				count++;
			}
			count++;
			System.out.println("Here is 4");
			str = "INSERT INTO `elab`.`GroundTruth` (`idKnowledges`, `Operator`, `Operand1`, `Operand2`, `Output`) VALUES ("
					+ count
					+ ", '"
					+ queryOperator
					+ "', '"
					+ a1
					+ "', '"
					+ a2
					+ "', '" + a3 + "');";

			System.out.println("Here is 5");
			System.out.println(str);
			statement.executeUpdate(str);

		} catch (SQLException e) {
			System.err.print(e.getMessage() + " ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " FUUUUUUUUUU!");
		}
		return (double) (output);
	}

	public static interface DoStuff {

		public void doStuff();
	}

}
