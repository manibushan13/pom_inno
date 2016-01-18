//package HA.Utilities;
//
//import java.awt.image.BufferedImage;
//import java.awt.image.DataBuffer;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.imageio.ImageIO;
//
//import HA.SeleniumLib.Common;
//import HA.TestAutomation.Driver;
//import HA.TestAutomation.HATF_properties;
//
//public class File1 {
//
//	public static HATF_properties _properties = new HATF_properties();
//
//	//public static String targetfilepath=null; 
//
//	public static void fcompare(String xmltext,String expecteddata) {
//		try{
//			String comparatorpath = System.getProperty("user.dir")+"\\Lib\\SpreadSheetComparator.exe";
//			String compareresults = System.getProperty("user.dir")+"\\TestLogs\\Compare Results\\";
//			String strDate = HA.Utilities.HTML.GetDateTime();
//			String targetfilepath=null; 
//
//
//			targetfilepath = compareresults+"target"+strDate+".xml";
//			java.io.FileWriter fw = new java.io.FileWriter(targetfilepath);
//			fw.write(xmltext);
//			fw.close();
//			System.out.println("Target file :"+targetfilepath);
//			System.out.println("Comparator Path :"+comparatorpath);
//			//ProcessBuilder pb = new ProcessBuilder("C:\\Host.SpreadSheet.Comparator\\Host.SpreadSheet.Comparator\\bin\\Debug\\Host.SpreadSheet.Comparator.exe", "objSpreadsheet", "C:\\CPMAutomation\\hatf\\QA\\TestData\\NonUnicode\\DynamicReports\\Verify\\REP_DYN_DR_POV_MultimemberSelection_POV_Applied_VerifyData.xml", "C:\\CPMAutomation\\hatf\\QA\\TestData\\NonUnicode\\DynamicReports\\Verify\\REP_DYN_DR_POV_MultimemberSelection_POV_Applied_VerifyData.xml", "Verify", "C:\\CPMAutomation\\");
//			//ProcessBuilder pb = new ProcessBuilder(comparatorpath, "objSpreadsheet", "C:\\Verify\\VerifyData.xml", targetfilepath, "Verify", "C:\\Verify\\");
//			ProcessBuilder pb = new ProcessBuilder(comparatorpath, "objSpreadsheet", expecteddata, targetfilepath, "Verify", compareresults);
//			
//			pb.start();
//			
//			try
//			{
//			//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(shell.getInputStream()));
//			StringBuilder response=new StringBuilder();
//			String line;
//			//while ((line = bufferedReader.readLine()) != null) {
//			response.append(line + "\n");
//			}
//			//bufferedReader.close();
//			if (response.toString().length() > 0) {
//			//out=response.toString().trim();
//			}
//
//			}catch(Exception e){
//			out=e.toString();
//			Driver.logger.error(e);
//			}
//			System.out.println(out);
//
//			
//			
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			Driver.logger.error(e);
//		}
//
//	}
//	
//	public static String GetDateTime()
//	{
//	String strDate,sDateTime = null;
//	try{
//
//	SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
//
//	SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
//
//	Date now = new Date();
//
//	strDate = sdfDate.format(now);
//
//	String strTime = sdfTime.format(now);
//
//	strTime = strTime.replace(":", "-");
//
//	sDateTime = strDate + "_" + strTime;}
//
//	catch (Exception e){
//
//	e.printStackTrace();
//	}
//	return sDateTime;
//	}
//	
//	public static boolean compareImage(String sourceImage, String targetImage) { 
//		try {
//			String ss= GetDateTime()+".jpg";			
//			Common.screenShot(targetImage+ss);
//			
//			// read buffer data from image files // 
//			java.io.File ImageA = new java.io.File(sourceImage);
//			java.io.File ImageB = new java.io.File(targetImage+ss);
//			BufferedImage biA = ImageIO.read(ImageA); 
//			DataBuffer dbA = biA.getData().getDataBuffer(); 
//			int sizeA = dbA.getSize(); 
//			BufferedImage biB = ImageIO.read(ImageB); 
//			DataBuffer dbB = biB.getData().getDataBuffer(); 
//			int sizeB = dbB.getSize(); 
//			// compare data-buffer objects // 
//			if(sizeA == sizeB) { 
//				for(int i=0; i<sizeA; i++) { 
//					if(dbA.getElem(i) != dbB.getElem(i)) { 
//						Driver.logger.error("sourceImage:  "+sourceImage+"\ntargetImage:  "+targetImage+ss+"\nResult:  failed");
//						return false; 
//					} 
//				} 
//				Driver.logger.info("sourceImage:  "+sourceImage+"\n targetImage:  "+targetImage+ss+"\nResult:  PASSED");
//				return true; 
//			} 
//			else { 
//				Driver.logger.error("sourceImage:  "+sourceImage+"\n targetImage:  "+targetImage+ss+"\nResult:  failed");
//				return false; 
//			} 
//		} 
//		catch (Exception e) { 
//			Driver.logger.error("Failed to compare image files ...\n"+e);
//			return false; 
//		} 
//	}
//
//
//	//    public static void writeToxmlfile(String xmltext) throws Exception
//	//    {
//	//    	String strDate = HA.Utilities.HTML.GetDateTime();
//	//    	targetfilepath = "C:/Verify/target"+strDate+".xml";
//	//    	java.io.FileWriter fw = new java.io.FileWriter(targetfilepath);
//	//        fw.write(xmltext);
//	//        fw.close();
//	//
//	//    }
//
//
//}
