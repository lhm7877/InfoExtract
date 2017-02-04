package com.HooMin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import ke.Ref;
import ke.RefSet;
import ke.ShellCommander;
import ke.��������;

class CRFmodel {
}

public class InfoExtractWithKE {
	public InfoExtractWithKE() {
		// TODO Auto-generated constructor stub
	}

	public InfoExtractWithKE(RefSet a) {
		// TODO Auto-generated constructor stub
	}

	public void InfoExtract(RefSet aRefSset, CRFmodel aCRFmodel) {
		// ���ο� Ŭ����, �Լ��� ����� ���� �Լ�
		// evolution: ������ �޾Ƶ鿩 �����θ� ������
		System.out.print("���� �Լ�infoExtract ���� ��");
	}

	public void f() {
		System.out.print("���� f�Լ� ���� ��");
	}

	// �ܺ����Ŀ� ���ؼ� ���� �˰��� �Լ��� ����� �� �ִ� �Լ�
	private void knowledgeEmbedding2(String fromKnow, String propertyName) {
		String parameterType = "";
		// ���� Ŭ������ ��� �Լ� ������ ����ϴ� �ڵ�
		System.out.println(this.getClass().getName());
		Class<?> cls;
		Method[] m;
		Class<?>[] aParam = null;
		try {
			cls = this.getClass();
			// Field[] fd =c.getDeclaredFields();
			m = cls.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				// System.out.println("*******"+m[i].getName());
				if (m[i].getName() == fromKnow.toString()) {
					aParam = m[i].getParameterTypes();
					for (int j = 0; j < aParam.length; j++)
						parameterType = aParam[j].getSimpleName();
					System.out.println(m[i].getName());
					System.out.println(" will be replaced by ");
				} else {
					// System.out.println(fd[i].toString());
					// System.out.println(m[i].getName());
				}
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
		String newClassName = fromKnow + "_" + propertyName;
		// ���� Ŭ������ �ڵ带 ���Ӱ� �ٹ̴� �κ�. Ư��, ����� �ܺ� ������ ���ؼ� ���θ� �����ϴ� �κ��� ���Եȴ�.
		String str = "public class " + newClassName + "\n" // Ŭ���� ����
				+ "{ \n" + "	public " + newClassName + "() {\n" // Ŭ���� ������
				+ "\t} \n"
				// for (int i = 0; i < toKnow.size(); i++)
				// retrieveSourcesFromDB(����);
				+ "\tString classifier_by_style;\n" + "	public void " + newClassName + "(";
		for (int j = 0; j < aParam.length; j++) {
			str += parameterType = aParam[j].getSimpleName();
			str += " a" + parameterType;
			if (j == aParam.length - 1)
				break;
			str += ", ";
		}
		str += ") {\n" // �ܺ����Ŀ��� ���õ� �Լ� < ��� �� �κ��� DB���� �˻��� ����
				// �����;� �ϴ� �κ�
				// + " System.out.println(\"���� "+toKnow[0]+"�Լ� ���� ��\");\n"
				+ " \t\tif (classifier_by_style == \"\")\n" + " \t\t\tClassifier.learn(traindata, modelname);\n"
				+ " \t\tClassifier.classify(aRef, modelname);\n" + "\t\t" + fromKnow + "(";
		for (int j = 0; j < aParam.length; j++) {
			str += " a" + aParam[j].getSimpleName();
			if (j == aParam.length - 1)
				break;
			str += ", ";
		}
		str += ".key(aRef.style));\n" + "\t} \n"
		// + " public void "+ toKnow[1]+"() {\n" // �ܺ����Ŀ��� ���õ� �Լ� ���� <- ��� �� �κ���
		// �����ͺ��̽����� �˻��� ���� �����;� �ϴ� �κ�
		// + " System.out.println(\"���� "+toKnow[1]+"�Լ� ���� ��\");\n"
		// + "} \n"
				+ "	public static void main(String[] args) {\n" // �����Լ�
				+ "	" + newClassName + " aThisClass = new " + newClassName + "();\n" // ����
																						// Ŭ����
																						// ����
				+ "\t\taThisClass." + newClassName + "(";
		for (int j = 0; j < aParam.length; j++) {
			str += " a" + aParam[j].getSimpleName();
			if (j == aParam.length - 1)
				break;
			str += ", ";
		}
		str += ");\n" // �ܺ����Ŀ��� ���õ� �Լ�
				// + " aThisClass."+toKnow[1]+"();\n" + " }\n"
				+ "}\n";
		// System.out.print(str);
		// File Writing
		// FileInputStream f = new FileInputStream("i");
		// FileOutputStream outfile = new
		// FileOutputStream(this.getClass().getName()+"_x.java");
		try {
			// str�� ���Ͽ� ����.
			FileWriter os = new FileWriter(this.getClass().getName() + "_x.java");
			for (int x = 0; x < str.length(); x++) {
				os.write(str.charAt(x)); // writes the bytes
			}
			os.close();
			// �� ������ �о ����Ѵ�.
			FileReader is = new FileReader(this.getClass().getName() + "_x.java");
			int c;// = is..read.available();
			while ((c = is.read()) != -1) {
				System.out.print((char) c);
			}
			// for(int i=0; i< size; i++){
			// System.out.print((char)is.read() + "");
			// }
			is.close();
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		// ���� Ŭ������ �н�
		String algoPath = this.getClass().getClassLoader().getResource("").getPath();
		// ���� Ŭ������ �̸��� �ٲ�
		String algoName = this.getClass().getName() + "_x.java";
		// ���� �۾� ���丮 ���
		String workingdirectory = System.getProperty("user.dir");
		System.out.println("Current working dirctory: " + workingdirectory);
		// ���� Ŭ������ �ڹ� ������ ��ɾ� ����
		String cmd = "javac " + workingdirectory + "/" + algoName;
		System.out.println("algoPath+algoName:" + algoPath + algoName);
		System.out.println("workingdirectory+algoName" + workingdirectory + "/src/" + algoName);
		System.out.println("cmd:" + cmd);
		try {
			// ���� Ŭ���� ������
			ShellCommander.shellCmd(cmd);
			// cmd = "java -cp " +algoPath.subSequence(0,
			// algoPath.lastIndexOf("/")) + " "+algoName.subSequence(0,
			// algoName.indexOf(".java")) ;
			// ���� Ŭ���� ���� ��ɾ� ����
			cmd = "java -cp " + workingdirectory + "/ " + algoName.subSequence(0, algoName.indexOf(".java"));
			System.out.println(cmd + "\n");
			// ���� Ŭ���� ����
			ShellCommander.shellCmd(cmd);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	// �ܺ����Ŀ� ���ؼ� ���� �˰��� �Լ��� ����� �� �ִ� �Լ�
	private void knowledgeEmbedding(String[] fromKnow, String[] toKnow) {
		// ���� Ŭ������ ��� �Լ� ������ ����ϴ� �ڵ�
		System.out.println(this.getClass().getName());
		try {
			Class c = this.getClass();
			Field[] fd = c.getDeclaredFields();
			Method[] m = c.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName() == fromKnow[0].toString()) {
					System.out.println(m[i].getName());
					System.out.println(" will be replaced by " + toKnow[0] + ", " + toKnow[1]);
				} else {
					// System.out.println(fd[i].toString());
					System.out.println(m[i].getName());
				}
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
		// ���� Ŭ������ �ڵ带 ���Ӱ� �ٹ̴� �κ�. Ư��, ����� �ܺ� ������ ���ؼ� ���θ� �����ϴ� �κ��� ���Եȴ�.
		String str = "public class " + this.getClass().getName() + "_x\n" // Ŭ����
																			// ����
				+ "{ \n" + "	public " + this.getClass().getName() + "_x() {\n" // Ŭ����
																					// ������
				+ "} \n"
				// for (int i = 0; i < toKnow.size(); i++)
				// retrieveSourcesFromDB(����);
				+ "	public void " + toKnow[0] + "() {\n" // �ܺ����Ŀ��� ���õ� �Լ� < ��� ��
															// �κ��� DB���� �˻��� ����
				// �����;� �ϴ� �κ�
				+ "	System.out.println(\"���� " + toKnow[0] + "�Լ� ���� ��\");\n" + "} \n" + "	public void " + toKnow[1]
				+ "() {\n" // �ܺ����Ŀ��� ���õ� �Լ� ���� <- ��� �� �κ���
				// �����ͺ��̽����� �˻��� ���� �����;� �ϴ� �κ�
				+ "	System.out.println(\"���� " + toKnow[1] + "�Լ� ���� ��\");\n" + "} \n"
				+ "	public static void main(String[] args) {\n" // �����Լ�
				+ "	" + this.getClass().getName() + "_x aThisClass = new " + this.getClass().getName() + "_x();\n" // ����
																													// Ŭ����
																													// ����
				+ "     aThisClass." + toKnow[0] + "();\n" // �ܺ����Ŀ��� ���õ� ��
				+ "     aThisClass." + toKnow[1] + "();\n" + "	}\n" + "}";
		// System.out.print(str);
		// File Writing
		// FileInputStream f = new FileInputStream("i");
		// FileOutputStream outfile = new
		// FileOutputStream(this.getClass().getName()+"_x.java");
		try {
			// str�� ���Ͽ� ����.
			FileWriter os = new FileWriter(this.getClass().getName() + "_x.java");
			for (int x = 0; x < str.length(); x++) {
				os.write(str.charAt(x)); // writes the bytes
			}
			os.close();
			// �� ������ �о ����Ѵ�.
			FileReader is = new FileReader(this.getClass().getName() + "_x.java");
			int c;// = is..read.available();
			while ((c = is.read()) != -1) {
				System.out.print((char) c);
			}
			// for(int i=0; i< size; i++){
			// System.out.print((char)is.read() + "");
			// }
			is.close();
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		// ���� Ŭ������ �н�
		String algoPath = this.getClass().getClassLoader().getResource("").getPath();
		// ���� Ŭ������ �̸��� �ٲ�
		String algoName = this.getClass().getName() + "_x.java";
		// ���� �۾� ���丮 ���
		String workingdirectory = System.getProperty("user.dir");
		System.out.println("Current working dirctory: " + workingdirectory);
		// ���� Ŭ������ �ڹ� ������ ��ɾ� ����
		String cmd = "javac " + workingdirectory + "/" + algoName;
		System.out.println("algoPath+algoName:" + algoPath + algoName);
		System.out.println("workingdirectory+algoName" + workingdirectory + "/src/" + algoName);
		System.out.println("cmd:" + cmd);
		try {
			// ���� Ŭ���� ������
			ShellCommander.shellCmd(cmd);
			// cmd = "java -cp " +algoPath.subSequence(0,
			// algoPath.lastIndexOf("/")) + " "+algoName.subSequence(0,
			// algoName.indexOf(".java")) ;
			// ���� Ŭ���� ���� ��ɾ� ����
			cmd = "java -cp " + workingdirectory + "/ " + algoName.subSequence(0, algoName.indexOf(".java"));
			System.out.println(cmd + "\n");
			// ���� Ŭ���� ����
			ShellCommander.shellCmd(cmd);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public static void main(String[] args) {
		ArrayList<��������> CORA = new ArrayList<��������>();
		// �Է����������� �迭����Ʈ�� �� ref�� �̸��� StyleID�� �����Ѵ�.
		// ���� ���, ref�̸�: ref0, styleID: 1
		for (int i = 0; i < 5; i++) {
			Ref aRef = new Ref("ref" + i, i % 2 + 1);
			CORA.add(aRef);
		}
		RefSet a = new RefSet();
		a.���ں���(CORA); // Style ID �� ���� �Է� �ڷḦ �����Ѵ�. ��, �ϳ��� classifier�� �ش��Ѵ�.
		HashMap<Integer, ArrayList<��������>> resultSplitted;
		resultSplitted = a.splitted_set_t;
		// ���ں����� ����� �̷�������� RefSet a ����ϱ�
		for (Integer i : resultSplitted.keySet()) {
			System.out.print("style " + i + ":");
			// �� ��Ÿ�Ϻ�
			int j = resultSplitted.get(i).size();
			// ArrayList<��������>
			for (�������� k : resultSplitted.get(i)) {
				System.out.print(k.refValue + ",");
			}
			System.out.println("");
		}
		// create an info extraction class using knowledge embedding
		InfoExtractWithKE aIE = new InfoExtractWithKE();
		CRFmodel crfmodel = new CRFmodel();
		// aIE.InfoExtract(a, crfmodel);
		// where can we get this external knowledge
		String[] fromKnow = { "f" }; // this is the original path
		String[] toKnow = { "g", "h" }; // this would be another path. this can
		// be found by 'Redescription' data
		// mining..
		// knowledge embedding: executing info extraction class
		// aIE.knowledgeEmbedding(fromKnow, toKnow);
		aIE.knowledgeEmbedding2("InfoExtract", "Style");
	}
}