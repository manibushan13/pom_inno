package HA.Utilities;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Util {
	
	public static HashMap<String, String> locaterType(String locaterPage) throws Exception
	{
		HashMap<String, String> locaterType = new HashMap<String, String>();
		System.out.println("locaterPage:::::::"+locaterPage);
		FileInputStream file1 = new FileInputStream(new File("D:\\poc\\sample\\src\\files\\pom.xls"));   
		HSSFWorkbook workbook = new HSSFWorkbook(file1);  
		HSSFSheet sheet = workbook.getSheet(locaterPage);  

		int rowNum = sheet.getLastRowNum()+1;

		for (int i=1; i<rowNum; i++){
			HSSFRow row = sheet.getRow(i);
			int j=0;
			HSSFCell cell = row.getCell(j);
			String eleKey = cellToString(cell);
			
			int k=j+1;
			HSSFCell type1 = row.getCell(k);
			String eleType = cellToString(type1);
			System.out.println("Ele key : " + eleKey);
			System.out.println("Ele Type:"+eleType);
			locaterType.put(eleKey, eleType);
			}
		System.out.println("Successfully Fetched "+locaterPage+" Page Locaters.");
		return locaterType;
	}
	
	public static HashMap<String, String> locaterText(String locaterPage) throws Exception
	{
		HashMap<String, String> locaterText = new HashMap<String, String>();
		FileInputStream file1 = new FileInputStream(new File("D:\\poc\\sample\\src\\files\\pom.xls"));   
		HSSFWorkbook workbook = new HSSFWorkbook(file1);  
		HSSFSheet sheet = workbook.getSheet(locaterPage);  

		int rowNum = sheet.getLastRowNum()+1;

		for (int i=1; i<rowNum; i++){
			HSSFRow row = sheet.getRow(i);
			int j=0;
			HSSFCell cell = row.getCell(j);
			String eleKey = cellToString(cell);
			
			int l=j+2;
			HSSFCell text1 = row.getCell(l);
			String eleText = cellToString(text1);
			System.out.println("Ele key : " + eleKey);
			System.out.println("Ele Text:"+eleText);
			locaterText.put(eleKey, eleText);
			}
		System.out.println("Successfully Fetched "+locaterPage+" Page Locaters.");
		return locaterText;
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
	
	public static String getXmlData(String datafile,String datasetName,String value1) throws Exception 
	{

//		File file = new File(System.getProperty("user.dir")+"/src/HA/TestData/"+datafile);
		File file = new File(datafile);
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

		return values;

	}


}
