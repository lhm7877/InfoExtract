package com.HooMin;

public class repeat{
	public String repeat(String subjClassName, String objOperName,
			String objClassName, String source) {
		String str = source + "\n" + "public class "
				+ subjClassName+"_"+objClassName
				+ "{ \n"
				+ objClassName
				+ " a1 = new "
				+ objClassName
				+ "();\n"
				+ "	public "
				+ subjClassName+"_"+objClassName
				+ "() {\n"
				+ "} \n"
				+ "\n"
				+ "	public Double recur( Double n, Double k) {\n"
				+ "	System.out.println(\"here is in the recur. n: \" + n + \", k\" + k);\n"
				+ "	if (n == 0) {\n"
				+ "	System.out.println(\"here is in the recur2. n: \" + n + \", k\" + k);\n"
				+ "	  return 0.0;\n"
				+ "	}\n"
				+ "	    if (n > 0)\n"
				+ "	    {\n"
				+ "	System.out.println(\"here is in the recur3. n: \" + n + \", k\" "
				+ "+ k);\n"
				+ "	    return a1."
				+ objClassName
				+ "((Double)(recur(n-1, k)), k);\n"
				+ "	    }\n"
				+ "	    else\n"
				+ "	    {\n"
				+ "	System.out.println(\"here is in the recur3. n: \" "
				+ "+ n + \", k\" "
				+ "+ k);\n"
				+ "	    return a1."
				+ objClassName
				+ "((Double)( recur( n+1, k)) ,- k);\n"
				+ "	    }\n "
				+ "	}\n "
				+ "	public static void main(String[] args) {\n" 
				+ "	" + subjClassName+"_"+objClassName + " aThisClass = new "
				+ subjClassName+"_"+objClassName
				+ "();\n" 
				+ "     Double a = aThisClass.recur(10.0, 2.0);\n"
				+ "System.out.println(\"\"+a);" + "}\n" + "}";
		System.out.print(str);
		return str;
	}

	public static void main(String[] args) {
		repeat rp = new repeat();
		String str = "�ݺ�";
		String str1 = "���ϱ�";
		String className = "add";
		String source =  "source";
		System.out.println("***********str: " + str + ", str1: " + str1
				+ ", className: " + className + "********");
		String newstr = rp.repeat(str, str1, className, source);
	}
}