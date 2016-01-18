package HA.Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Txtfilecreation {
	
	static String fname = "";
	static PrintWriter out1 = null;
	static File file = null;
	
	public static void filecreation()
	{
	
	
	
	Date date = new Date() ;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
//	 file = new File("C:/hataf/source/TestLogs/Listener txt files/"+dateFormat.format(date) + ".txt") ;
	file = new File(System.getProperty("user.dir")+"/TestLogs/Listener txt files/"+dateFormat.format(date) + ".txt") ;
	
//	try {
//		 fname = file.getCanonicalPath();
//	} catch (IOException e2) {
//		// TODO Auto-generated catch block
//		e2.printStackTrace();
//	}
//	//BufferedWriter out = null;
//	try {
//		//out = new BufferedWriter(new FileWriter(file));
//		 out1 = new PrintWriter(new BufferedWriter(new FileWriter(fname, true)));
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	//out.write("Writing to file");
//	out1.println("hmmm");
//	out1.println("hm2222222222222mm");
//	out1.println("hm33333333333333mm");
	//out1.close();
	}
	
	public static void filereadyforwritingdata()
	{
		try {
			 fname = file.getCanonicalPath();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//BufferedWriter out = null;
		try {
			//out = new BufferedWriter(new FileWriter(file));
			 out1 = new PrintWriter(new BufferedWriter(new FileWriter(fname, true)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//out.write("Writing to file");
		
		
	}
	
	public static void filewrite(String s)
	{
		out1.println(s);
	}
	
	public static void fileclose()
	{
		out1.close();
	}

}
