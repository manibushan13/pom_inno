package HA.Utilities;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import HA.Properties.HATF_properties;
import HA.Properties.logApp;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadXML 
{
	public static HATF_properties _properties = new HATF_properties();

	public static String dataset(String datafile,String datasetName,String value1) throws Exception 
	{

		File file = new File(System.getProperty("user.dir")+"/src/HA/TestData/"+datafile);
//		File file = new File(_properties.getProperty(HATF_properties.TEST_DATA)+datafile);
		String values = null;		 

		if(file.getName().endsWith(".xml")){

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			String DataSetName = "DataSet";
			String DataAttributeName="Add";
			String keyvalue="key";
			String Value="value";

			NodeList nList = doc.getElementsByTagName(DataSetName);

			for (int temp = 0; temp < nList.getLength(); temp++) 
			{
				Node tableNode = nList.item(temp);
				Element tableElement = (Element) tableNode;	

				if(datasetName.equals(tableElement.getAttribute("name")))		     
				{		    	  
					NodeList columnList = tableElement.getElementsByTagName(DataAttributeName);
					for (int j = 0; j < columnList.getLength(); j++) 
					{
						Node columnNode = columnList.item(j);
						Element columnElement = (Element) columnNode;				         

						if(value1.equals(columnElement.getAttribute(keyvalue)))
							values=columnElement.getAttribute(Value);

					}
				}
			}
		}


		if(file.getName().endsWith(".xls")){
			try{

				FileInputStream file1 = new FileInputStream(new File(System.getProperty("user.dir")+"/src/HA/TestData/"+datafile));   
				HSSFWorkbook workbook = new HSSFWorkbook(file1);  

				HSSFSheet sheet = workbook.getSheet(datasetName);  

				int rowNum = sheet.getLastRowNum()+1;
				int colNum = sheet.getRow(0).getLastCellNum();		    


				for (int i=0; i<rowNum; i++){
					HSSFRow row = sheet.getRow(i);
					for (int j=0; j<colNum; j++){
						Cell cell = row.getCell(j);
						String value = cellToString(cell);
						if(value1.equals(value)){
							values=cellToString(row.getCell(j+1));
							System.out.println("The key is" + value);
							System.out.println("The value is" + values);
						}

					}
				}

				file1.close();  

			} catch (FileNotFoundException fnfe) {  
				fnfe.printStackTrace();  
			} catch (IOException ioe) {  
				ioe.printStackTrace();  
			} 
		}


		if(file.getName().endsWith(".xlsx")){

			FileInputStream file1 = new FileInputStream(new File(System.getProperty("user.dir")+"/src/HA/TestData/"+datafile));   
			XSSFWorkbook workbook = new XSSFWorkbook(file1);  

			XSSFSheet sheet = workbook.getSheet(datasetName);  

			int rowNum = sheet.getLastRowNum()+1;
			int colNum = sheet.getRow(0).getLastCellNum();

			loop:
				for (int i=0; i<rowNum; i++){
					Row row = sheet.getRow(i);
					for (int j=0; j<colNum; j++){
						Cell cell = row.getCell(j);
						String value = cellToString(cell);
						if(value1.equals(value)){
							values=cellToString(row.getCell(j+1));
							System.out.println("The key is :"+value);
							System.out.println("The value is :"+values);
							break loop;
						}	                	

					}
				}

			file1.close(); 
		}

		return values;

	}

	public static String cellToString (Cell cell) throws Exception{

		int type;
		Object result;
		type = cell.getCellType();

		switch(type) {

		case 0://numeric value in excel
			DataFormatter fmt = new DataFormatter();
			result =  fmt.formatCellValue(cell);
			break;
		case 1: //string value in excel
			result = cell.getStringCellValue();
			break;
		case 2: //boolean value in excel
			result = cell.getBooleanCellValue ();
			break;
		default:
			throw new Exception("This type of cell is not supported.");                      
		}
		return result.toString();
	}


	public static void owcdata(String datafilepath,String owcname, String owcsheetname) throws Exception 
	{

		File fXmlFile = new File(datafilepath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		String DataSetName = "SheetData";
		String DataAttributeName="Data";
		//String datasheetName = "Currency Exchange Rate";
		// String datasheetName = (String) Common.owcSheetName(owcname);
		String datasheetName = owcsheetname;
		//String datasheetName = (String) Common.owcSheetName("ssClient");
		//String datasheetName = (String) Common.owcSheetName("objSpreadsheet");
		System.out.println("Here Mankoo...");
		//System.out.println(Common.owcSheetName("ssClient"));
		String col="column";
		String row="row";
		String Value="value";

		String values = null;
		String colnum = null;
		String rownum = null;

		NodeList nList = doc.getElementsByTagName(DataSetName);

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node tableNode = nList.item(temp);
			Element tableElement = (Element) tableNode;	

			if(datasheetName.equals(tableElement.getAttribute("sheetName")))		     
			{		    	  
				NodeList columnList = tableElement.getElementsByTagName(DataAttributeName);
				for (int j = 0; j < columnList.getLength(); j++) 
				{
					Node columnNode = columnList.item(j);
					Element columnElement = (Element) columnNode;				         

					colnum=columnElement.getAttribute(col);
					rownum=columnElement.getAttribute(row);
					values=columnElement.getAttribute(Value);
					HA.SeleniumLib.Common.owcInput(rownum,colnum,values,owcname);
				}
			}
		}

	}


	public static String getEquivColumn(String number) {

		String converted = "";
		try{
			int num = Integer.parseInt(number);
			num = num-1;
			while (num >= 0)
			{
				int remainder = num % 26;
				converted = (char)(remainder + 'A') + converted;
				num = (num / 26) - 1;
			}
		}

		catch(Exception e){
			e.printStackTrace();
		}
		return converted;
	}





	public static void generateHTML() throws Exception{

		File file = new File(System.getProperty("user.dir") + "/test-output/"+"testng-results.xml");
		String values;
		//String value1 = null,datasetName = null;

		if(file.getName().endsWith(".xml")){

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			String suiteName = "suite";
			String testName = "test";
			String className = "class";
			//String testmethod="test-method";
			
			String teststatus="status";
			
			NodeList resultNode = doc.getElementsByTagName("testng-results");
			
			for (int i = 0; i < resultNode.getLength(); i++) 
			{
				Node rNode = resultNode.item(i);
			
			Element resultElement = (Element) rNode;
			
			String skipped = resultElement.getAttribute("skipped");
			String failed = resultElement.getAttribute("failed");
			String total = resultElement.getAttribute("total");
			String passed = resultElement.getAttribute("passed");
			
			
			logApp.logger.info("No of test skipped: "+skipped);
			logApp.logger.info("No of test failed: "+failed);
			logApp.logger.info("No of test total: "+total);
			logApp.logger.info("No of test passed: "+passed);
			
			NodeList sList = doc.getElementsByTagName(suiteName);
			for (int temp = 0; temp < sList.getLength(); temp++) 
			{
				Node testNode = sList.item(temp);
				
				Element suiteElement = (Element) testNode;	
				
				NodeList nList = suiteElement.getElementsByTagName(testName);


				for (int k = 0; k < nList.getLength(); k++) 
				{
					
					
					Node testNode1 = nList.item(k);
					
					Element tableElement = (Element) testNode1;	

							    	  
						NodeList testList = tableElement.getElementsByTagName(className);
						for (int j = 0; j < testList.getLength(); j++) 
						{
							
							String testscriptname = tableElement.getAttribute("name");
							logApp.logger.info("Test Script name: "+testscriptname);
							
							Node columnNode = testList.item(j);
							Element columnElement = (Element) columnNode;				         

							
								values=columnElement.getAttribute(teststatus);
								if(values.equals("PASS")){
									logApp.logger.info("Test :"+columnElement.getAttribute("signature")+" is passed");
								}
								

						}
						
						logApp.logger.info("Test :"+tableElement.getAttribute("class")+" is passed");
					
				}
				
			}
			}

		}
	}
	
	
	
}//eoc


