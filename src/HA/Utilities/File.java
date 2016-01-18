package HA.Utilities;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xslf.XSLFSlideShow;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.testng.Assert;

import HA.Properties.HATF_properties;
import HA.Properties.logApp;
import HA.SeleniumLib.Common;

public class File {

	public static HATF_properties _properties = new HATF_properties();

	//public static String targetfilepath=null; 

	public static void htmlFileCompare(String data, String expecteddata) {
		String out=null;
		try{
			String compareresults = System.getProperty("user.dir")+"/TestLogs/Compare Results/";
			String strDate = HA.Utilities.HTML.GetDateTime();
			String targetfilepath=null; 


			targetfilepath = compareresults+"target"+strDate+".html";
			java.io.File file = new java.io.File(targetfilepath);
			file.getParentFile().mkdirs();
			file.createNewFile();
			java.io.FileWriter fw = new java.io.FileWriter(file);
			fw.write(data);
			fw.close();
			System.out.println("Target file :"+targetfilepath);

			ProcessBuilder pb = new ProcessBuilder("java.exe", "-jar", String.format("\"%s\"", System.getProperty("user.dir")+"/Lib/daisydiff.jar"), String.format("\"%s\"", expecteddata), 
					String.format("\"%s\"", targetfilepath), String.format("\"--file=%s\"", compareresults + expecteddata.substring(expecteddata.lastIndexOf("/") + 1, expecteddata.lastIndexOf(".html")) + ".htm"));
			Process shell=pb.start();

			try
			{
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(shell.getInputStream()));
				StringBuilder response=new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					response.append(line + "\n");
				}
				bufferedReader.close();
				if (response.toString().length() > 0) {
					out=response.toString().trim();
				}
			}catch(Exception e){
				out=e.toString();
				logApp.logger.error(e);
			}
			System.out.println(out);

		} catch (Exception e) {
			e.printStackTrace();
			logApp.logger.error(e);
		}
	}

	public static void fcompare(String xmltext,String expecteddata) {
		String out=null;
		try{
			String comparatorpath = System.getProperty("user.dir")+"/Lib/SpreadSheetComparator.exe";
			String compareresults = System.getProperty("user.dir")+"/TestLogs/Compare Results/";
			String strDate = HA.Utilities.HTML.GetDateTime();
			String targetfilepath=null; 


			targetfilepath = compareresults+"target"+strDate+".xml";
			java.io.FileWriter fw = new java.io.FileWriter(targetfilepath);
			fw.write(xmltext);
			fw.close();
			System.out.println("Target file :"+targetfilepath);
			System.out.println("Comparator Path :"+comparatorpath);
			//ProcessBuilder pb = new ProcessBuilder("C:/Host.SpreadSheet.Comparator/Host.SpreadSheet.Comparator/bin/Debug/Host.SpreadSheet.Comparator.exe", "objSpreadsheet", "C:/CPMAutomation/hatf/QA/TestData/NonUnicode/DynamicReports/Verify/REP_DYN_DR_POV_MultimemberSelection_POV_Applied_VerifyData.xml", "C:/CPMAutomation/hatf/QA/TestData/NonUnicode/DynamicReports/Verify/REP_DYN_DR_POV_MultimemberSelection_POV_Applied_VerifyData.xml", "Verify", "C:/CPMAutomation/");
			//ProcessBuilder pb = new ProcessBuilder(comparatorpath, "objSpreadsheet", "C:/Verify/VerifyData.xml", targetfilepath, "Verify", "C:/Verify/");
			ProcessBuilder pb = new ProcessBuilder(comparatorpath, "objSpreadsheet", expecteddata, targetfilepath, "Verify", compareresults);
			Process shell=pb.start();
			try
			{
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(shell.getInputStream()));
				StringBuilder response=new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					response.append(line + "\n");
				}
				bufferedReader.close();
				if (response.toString().length() > 0) {
					out=response.toString().trim();
				}
			}catch(Exception e){
				out=e.toString();
				logApp.logger.error(e);
			}
			logApp.logger.info(out);
			//			System.out.println(out);

		} catch (Exception e) {
			e.printStackTrace();
			logApp.logger.error(e);
		}

	}
	
	public static void excelCompare(String expectedfilepath,String actualfilepath) throws Exception {
		String out=null;
		try{
			String comparatorpath = System.getProperty("user.dir")+"/Lib/SpreadSheetComparator.exe";
			String compareresults = System.getProperty("user.dir")+"/TestLogs/Compare Results/";


			ProcessBuilder pb = new ProcessBuilder(comparatorpath, "objSpreadsheet", expectedfilepath, actualfilepath, "Verify", compareresults);
			Process shell=pb.start();
			try
			{
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(shell.getInputStream()));
				StringBuilder response=new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					response.append(line + "\n");
				}
				bufferedReader.close();
				if (response.toString().length() > 0) {
					out=response.toString().trim();
					
				}
			}catch(Exception e){
				out=e.toString();
				logApp.logger.error(e);
			}
			logApp.logger.info(out);
			//			System.out.println(out);

		} catch (Exception e) {
			e.printStackTrace();
			logApp.logger.error(e);
			
		}

	}

	public static String GetDateTime()
	{
		String strDate,sDateTime = null;
		try{

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");

			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

			Date now = new Date();

			strDate = sdfDate.format(now);

			String strTime = sdfTime.format(now);

			strTime = strTime.replace(":", "-");

			sDateTime = strDate + "_" + strTime;}

		catch (Exception e){

			e.printStackTrace();
		}
		return sDateTime;
	}

	
	public static boolean comparexls(String datafile, String dataset) throws Exception{		
		/* Get the files to be compared first */
		String expectedfile = Common.Getxml(datafile, dataset, "expectedfile");
		String actualfile = Common.Getxml(datafile, dataset, "actualfile");
		java.io.File file1 = new java.io.File(expectedfile);
		java.io.File file2 = new java.io.File(actualfile);
        boolean compareResult = FileUtils.contentEquals(file1, file2);
        System.out.println("Are the files are same? " + compareResult);
        return compareResult;
	}

	
	public static boolean compareImage(String sourceImage, String targetImage) { 
		try {
			String ss= GetDateTime()+".jpg";			
		//	Common.screenShot(targetImage+ss);

			// read buffer data from image files // 
			java.io.File ImageA = new java.io.File(sourceImage);
			java.io.File ImageB = new java.io.File(targetImage+ss);
			System.out.println(targetImage+ss);
			BufferedImage biA = ImageIO.read(ImageA); 
			System.out.println("2");
			DataBuffer dbA = biA.getData().getDataBuffer(); 
			System.out.println("3");
			int sizeA = dbA.getSize(); 
			BufferedImage biB = ImageIO.read(ImageB); 
			DataBuffer dbB = biB.getData().getDataBuffer(); 
			int sizeB = dbB.getSize(); 
			System.out.println("4");
			// compare data-buffer objects // 
			if(sizeA == sizeB) { 
				for(int i=0; i<sizeA; i++) { 
					if(dbA.getElem(i) != dbB.getElem(i)) { 
						logApp.logger.error("sourceImage:  "+sourceImage+"\ntargetImage:  "+targetImage+ss+"\nResult:  failed");
						return false; 
					} 
				} 
				logApp.logger.info("sourceImage:  "+sourceImage+"\n targetImage:  "+targetImage+ss+"\nResult:  PASSED");
				return true; 
			} 
			else { 
				logApp.logger.error("sourceImage:  "+sourceImage+"\n targetImage:  "+targetImage+ss+"\nResult:  failed");
				return false; 
			} 
		} 
		catch (Exception e) { 
			logApp.logger.error("Failed to compare image files ...\n"+e);
			return false; 
		} 
	}

	public static boolean compareImages(String file1, String file2) { 
		try {
			java.io.File fileA=new java.io.File(file1);
			java.io.File fileB=new java.io.File(file2);
		// take buffer data from botm image files //
		BufferedImage biA = ImageIO.read(fileA);
		DataBuffer dbA = biA.getData().getDataBuffer();
		int sizeA = dbA.getSize(); 
		BufferedImage biB = ImageIO.read(fileB);
		DataBuffer dbB = biB.getData().getDataBuffer();
		int sizeB = dbB.getSize();
		// compare data-buffer objects //
		if(sizeA == sizeB) {
		for(int i=0; i<sizeA; i++) { 
		if(dbA.getElem(i) != dbB.getElem(i)) {
		return false;
		}
		}
		return true;
		}
		else {
		return false;
		}
		} 
		catch (Exception e) { 
		System.out.println("Failed to compare image files ...");
		return false;
		}
		}
	//    public static void writeToxmlfile(String xmltext) throws Exception
	//    {
	//    	String strDate = HA.Utilities.HTML.GetDateTime();
	//    	targetfilepath = "C:/Verify/target"+strDate+".xml";
	//    	java.io.FileWriter fw = new java.io.FileWriter(targetfilepath);
	//        fw.write(xmltext);
	//        fw.close();
	//
	//    }

	public static void WStest(String datafile, String dataset){
		try{
			String WSURL= _properties.getProperty(HATF_properties.WSURL);
			String WSUNAME=_properties.getProperty(HATF_properties.WSUNAME);
			String WSUPWD= _properties.getProperty(HATF_properties.WSPWD);
			String WSTENANT=_properties.getProperty(HATF_properties.WSTENANT);
			String out=null;

			if((System.getProperty("user.dir")+"/src/HA/TestData/DI/"+Common.Getxml(datafile,dataset, "FilePath")).endsWith(".csv"))
			{
				String WSExecute = System.getProperty("user.dir")+"/Lib/HostAPIWebserviceAuto.exe";
				ProcessBuilder pb = new ProcessBuilder(WSExecute, "-i","i","-u",WSURL,"-n",WSUNAME,"-p",WSUPWD,"-t",WSTENANT,"-d",",","-f",System.getProperty("user.dir")+"/src/HA/TestData/DI/"+Common.Getxml(datafile,dataset, "FilePath"),"-r",Common.Getxml(datafile,dataset, "DataloadName"));
				Process shell=pb.start();
				try
				{
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(shell.getInputStream()));
					StringBuilder response=new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						response.append(line + "\n");
					}
					bufferedReader.close();
					if (response.toString().length() > 0) {
						out=response.toString().trim();
					}
				}catch(Exception e){
					out=e.toString();
					logApp.logger.error(out);
				}

				if(out.contains("Transfer Data Done"))
					logApp.logger.info("..........Data Loaded Successfully through Web-Services");
				else
				{
					logApp.logger.info("..........Failed to Load the data through Web-Services with below exception");
					logApp.logger.info(out);
					Assert.fail();
					Common.closeAll();
				}
				logApp.logger.info(out);

			} else {
				logApp.logger.error("Please Use the csv file to load the data through Webservices");
			}
		}catch(Exception e){
			e.printStackTrace();
			logApp.logger.error(e);
		}

	}
	public static boolean wordFilesCompare(String sourceDoc, String destDoc) throws Exception {
		boolean result = false;
		XWPFDocument docx1 = new XWPFDocument(new FileInputStream(sourceDoc));
		XWPFWordExtractor we1 = new XWPFWordExtractor(docx1);
		XWPFDocument docx2 = new XWPFDocument(new FileInputStream(destDoc));
		XWPFWordExtractor we2 = new XWPFWordExtractor(docx2);
		System.out.println(we2.getText());
		//text comparision
		if(we1.getText().equals(we2.getText()))
		{
			System.out.println("Source Document and Destination Documents are equal");
			result = true;
		}
		else
		{
			System.out.println("Source Document and Destination Documents are not equal");
			result = false;
		}
		String[] sourcesplit=sourceDoc.split(".docx");
		String[] destsplit=destDoc.split(".docx");
		//embed objects comparision
		for(int i=0;i<docx2.getAllEmbedds().size();i++){
			PackagePart pPart= docx2.getAllEmbedds().get(i);
			String ct=pPart.getContentType();
			System.out.println(ct);
			String src=sourcesplit[0]+"_"+i;
			String dest=destsplit[0]+"_"+i;
			if(ct.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
				XSSFWorkbook embeddedWorkbook = new XSSFWorkbook(pPart.getInputStream());
				System.out.println(embeddedWorkbook.getSheet("Report"));
				FileOutputStream stream=new FileOutputStream( dest+".xlsx");
				embeddedWorkbook.write(stream);
				stream.close();
				excelCompare(src+".xlsx", dest+".xlsx");
			}
			else if(ct.equals("application/vnd.ms-excel")){
				HSSFWorkbook embeddedWorkbook = new HSSFWorkbook(pPart.getInputStream());
				System.out.println(embeddedWorkbook.getSheet("Report"));
				FileOutputStream stream=new FileOutputStream(dest+".xls");
				embeddedWorkbook.write(stream);
				stream.close();
				excelCompare(src+".xls", dest+".xls");
			}

		}
		//image comparision
		List<XWPFPictureData> piclist=docx2.getAllPictures();
		int count=0;
		System.out.println(piclist.size());
		for( int i=0;i<piclist.size();i++){
			System.out.println(piclist.get(i).getPictureType());
			String src=sourcesplit[0]+"_"+count;
			String dest=destsplit[0]+"_"+count;
			if(piclist.get(i).getPictureType()==5){
				byte[] bytepic=piclist.get(i).getData();
				BufferedImage imag=ImageIO.read(new ByteArrayInputStream(bytepic));
				if(imag!=null){
					ImageIO.write(imag, "jpg", new java.io.File(dest+".jpg"));
					count++;
					result=compareImages(src+".jpg", dest+".jpg");
				}
			}
			if(piclist.get(i).getPictureType()==6){
				byte[] bytepic=piclist.get(i).getData();
				BufferedImage imag=ImageIO.read(new ByteArrayInputStream(bytepic));
				if(imag!=null){
					ImageIO.write(imag, "png", new java.io.File(dest+".png"));
					count++;
					result=compareImages(src+".png", dest+".png");
				}
			}
			
		}
		return result;
	}
	
	
	public static boolean pptxfilescompare(String sourcePPT, String destPPT) throws Exception{
		boolean result = false;
		XSLFSlideShow pptx1 = new XSLFSlideShow(sourcePPT);
		XSLFPowerPointExtractor we1 = new XSLFPowerPointExtractor(pptx1);
		XSLFSlideShow pptx2 = new XSLFSlideShow(destPPT);
		XSLFPowerPointExtractor we2 = new XSLFPowerPointExtractor(pptx2);
		System.out.println(we2.getText());
		//text comparison
		if(we1.getText().equals(we2.getText()))
		{
			System.out.println("Source Document and Destination Documents are equal");
			result = true;
		}
		else
		{
			System.out.println("Source Document and Destination Documents are not equal");
			result = false;
		}
		String[] sourcesplit=sourcePPT.split(".pptx");
		String[] destsplit=destPPT.split(".pptx");

		//embed objects comparison
		for(int i=0;i<pptx2.getAllEmbedds().size();i++){
			PackagePart pPart= pptx2.getAllEmbedds().get(i);
			String ct=pPart.getContentType();
			System.out.println(ct);
			String src=sourcesplit[0]+"_"+i;
			String dest=destsplit[0]+"_"+i;
			if(ct.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
				XSSFWorkbook embeddedWorkbook = new XSSFWorkbook(pPart.getInputStream());
				System.out.println(embeddedWorkbook.getSheet("Report"));
				FileOutputStream stream=new FileOutputStream( dest+".xlsx");
				embeddedWorkbook.write(stream);
				stream.close();
				excelCompare(src+".xlsx", dest+".xlsx");
			}
			else if(ct.equals("application/vnd.ms-excel")){
				HSSFWorkbook embeddedWorkbook = new HSSFWorkbook(pPart.getInputStream());
				System.out.println(embeddedWorkbook.getSheet("Report"));
				FileOutputStream stream=new FileOutputStream(dest+".xls");
				embeddedWorkbook.write(stream);
				stream.close();
				excelCompare(src+".xls", dest+".xls");
			}

		}
		//image comparison
		XMLSlideShow pptx3 = new XMLSlideShow(new FileInputStream(destPPT));

		List<XSLFPictureData> piclist=pptx3.getAllPictures();
		System.out.println(piclist.size());int count=0;
		for( int i=0;i<piclist.size();i++){

			System.out.println(piclist.get(i).getPictureType());
			String src=sourcesplit[0]+"_"+count;
			String dest=destsplit[0]+"_"+count;
			if(piclist.get(i).getPictureType()==5){
				byte[] bytepic=piclist.get(i).getData();
				BufferedImage imag=ImageIO.read(new ByteArrayInputStream(bytepic));
				//System.out.println(imag);
				if(imag!=null){
					ImageIO.write(imag, "jpg", new java.io.File(dest+".jpg"));
					count++;
					result=compareImages(src+".jpg", dest+".jpg");
				}
			}
			if(piclist.get(i).getPictureType()==6){
				byte[] bytepic=piclist.get(i).getData();
				BufferedImage imag=ImageIO.read(new ByteArrayInputStream(bytepic));
				if(imag!=null){
					ImageIO.write(imag, "png", new java.io.File(dest+".png"));
					count++;
					result=compareImages(src+".png", dest+".png");
				}
			}
		}
		return result;

	}


	public static void textFileCompare(String actualFile, String expectedFile) throws Exception {
        String compareresults = System.getProperty("user.dir")+"/TestLogs/Compare Results/";
        String strDate = HA.Utilities.HTML.GetDateTime();
		String resultfilepath=null; 
		resultfilepath = compareresults+"TextCompareResults"+strDate+".xls";
		logApp.logger.info("In text File Compare method");
		try{
			// Create FileReader & Writer Objects.
            FileReader actualFileReader  = new FileReader(actualFile);
            FileReader expectedFileReader  = new FileReader(expectedFile);
            FileWriter resultFile = new FileWriter(resultfilepath);
            // Create Buffered Object.
            BufferedReader actualFileBufRdr = new BufferedReader(actualFileReader);
            BufferedReader expectedFileBufRdr = new BufferedReader(expectedFileReader);
            BufferedWriter resultFileBufWrtr = new BufferedWriter(resultFile);
           
            // Get the file contents into String Variables.
            String actualFileContent = actualFileBufRdr.readLine();
            String expectedtdFileContent = expectedFileBufRdr.readLine();
           
            // Write the content into the results file.
            resultFileBufWrtr.newLine();
            resultFileBufWrtr.write("  Comparision Results");
            resultFileBufWrtr.newLine();
            resultFileBufWrtr.newLine();
            resultFileBufWrtr.write("  Source	"+actualFile);
            resultFileBufWrtr.newLine();
            resultFileBufWrtr.write("  Target	"+expectedFile);
            resultFileBufWrtr.newLine();
            
            boolean isDifferent = false;
            int lineNumber = 1;
           
            if (actualFileContent != null || expectedtdFileContent != null) {
            	
            	resultFileBufWrtr.newLine();
                resultFileBufWrtr.write("    Line 		");
                resultFileBufWrtr.newLine();
               
                    // Check whether Actual file contains data or not
                    while((actualFileContent!=null)  ){
                           
                            // Check whether Expected file contains data or not
                            if (((expectedtdFileContent )!=null)) {
                                   
                                    // Check whether both the files have same data in the lines
                                    if (!actualFileContent.equals(expectedtdFileContent)) {
                                            resultFileBufWrtr.write(lineNumber+"		Source 		"+actualFileContent);
                                            resultFileBufWrtr.newLine();
                                            resultFileBufWrtr.write("		Target 		"+expectedtdFileContent);
                                            resultFileBufWrtr.newLine();
                                            isDifferent = true;
                                    }
                                    lineNumber = lineNumber+1;
                                    expectedtdFileContent= expectedFileBufRdr.readLine();
                            }
                            else{
                            		resultFileBufWrtr.write(lineNumber+"		Source 		"+actualFileContent);
                            		resultFileBufWrtr.newLine();
                            		resultFileBufWrtr.write("		Target 		"+expectedtdFileContent);
                            		resultFileBufWrtr.newLine();
                            		isDifferent = true;
                                    lineNumber = lineNumber+1;
                            }
                            actualFileContent=actualFileBufRdr.readLine();
                    }
                   
                    // Check for the condition : if Actual File has Data & Expected File doesn't contain data.
                    while ((expectedtdFileContent!=null)&&(actualFileContent==null)) {
                    		resultFileBufWrtr.write(lineNumber+"		Source 		"+actualFileContent);
                    		resultFileBufWrtr.newLine();
                    		resultFileBufWrtr.write("		Target 		"+expectedtdFileContent);
                    		resultFileBufWrtr.newLine();
                    		isDifferent = true;
                            lineNumber = lineNumber+1;
                            expectedtdFileContent= expectedFileBufRdr.readLine();
                    }
            }
            else{
                    // Mention that both the files don't have Data.
            	resultFileBufWrtr.newLine();    
            	resultFileBufWrtr.write("	No Data in both files");
                    resultFileBufWrtr.newLine();
                    isDifferent = true;
            }
           
            // Check is there any difference or not.
            if (isDifferent) {
            	
            }
            else{
                    resultFileBufWrtr.append("		No difference Found");
            }
           
            //Close the streams.
            actualFileReader.close();
            expectedFileReader.close();
            resultFileBufWrtr.close();
            actualFileBufRdr.close();
            expectedFileBufRdr.close();
            logApp.logger.info("Result generated successfully");
      }
		catch(Exception e)
		{
			e.printStackTrace();
			logApp.logger.error(e);
		}
}
	
	public static String[] readCSVFile(String file) throws Exception{
		BufferedReader CSVFile = new BufferedReader(new FileReader(file));
		  String str="";
		  String dataRow =""; 
		  while ((dataRow = CSVFile.readLine()) != null){
			  str=str.concat(dataRow+"@#@");
		  }
		  CSVFile.close();
		  String[] array = str.split("@#@");
		  return array;
		 } 

}//eoc
