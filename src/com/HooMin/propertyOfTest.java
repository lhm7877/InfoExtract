package com.HooMin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class propertyOfTest {
	static ResultSet rs = null;
	static String queryOperator;


	public void addField(String className, String propertyName, String propertyType) {
		CompilationUnit cu;
		queryOperator = "Ref";
		rs = ConnectionAlgoDB.getRelevantAlgorithm(queryOperator);
		String source = null;
		try {
			if (rs.next()) {
				source = rs.getString("Source");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cu = JavaParser.parse(source);

		ClassOrInterfaceDeclaration type = cu.getClassByName(className);
		EnumSet<Modifier> modifiers2 = EnumSet.of(Modifier.PUBLIC);
		FieldDeclaration field2 = new FieldDeclaration(modifiers2, new ClassOrInterfaceType(propertyType),
				new VariableDeclarator(new VariableDeclaratorId(propertyName)));
		type.addMember(field2);
		// ����ϱ�
		ConnectionAlgoDB.updateDB("algorithm",className+"_"+propertyName,className+"_"+propertyName,cu.toString());
	}
}