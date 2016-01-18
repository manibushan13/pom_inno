package HA.Utilities;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.collect.MapDifference;
//import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;

import HA.Properties.logApp;

public class Xmlcomparison {
	
	public static LinkedHashMap<String, List<String>> xmlreading(File fil) throws SAXException, IOException, ParserConfigurationException
	{
		return xmlreading(fil, 0, 16380);
	
	}
	
	public static Boolean compareResult(String fil1,String expfil1) throws SAXException, IOException, ParserConfigurationException
	{
		
		return compareResult(fil1, expfil1, 0, 16380);
	}
	
//	public static void main(String args[]) throws Exception
//	{
//		
//		
//		//File fil = new File("/Users/automationuser/Desktop/RT_CC_GLData_Actuals_AdhocReport1.xml");	
//		String fil = "C:/Users/ehasya/Desktop/test1.xml";
//		
//	
//		//File expfil = new File("/Users/automationuser/Desktop/RT_CC_GLData_Actuals_AdhocReport10.xml");	
//		String expfil = "C:/Users/ehasya/Desktop/test2.xml";
//
//		
//		System.out.println(Xmlcomparison.compareResult(fil, expfil));
//		
//        
//		
//		
//		
//	}
	
	
	
	
	
	public static LinkedHashMap<String, List<String>> xmlreading(File fil, int rowRange, int colRange) throws SAXException, IOException, ParserConfigurationException
	{
		System.out.println("one");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//		Document doc = dBuilder.parse(fil);
		
		InputStream inputStream= new FileInputStream(fil);
		Reader reader = new InputStreamReader(inputStream,"UTF-8");
		 
		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");
		
		Document doc = dBuilder.parse(is);

//		saxParser.parse(is, handler);Document doc = dBuilder.parse(fil);
		
//		Document expdoc = dBuilder.parse(expfil);
		
		
		LinkedHashMap<String, List<String>> map = new LinkedHashMap<String, List<String>>();	
		
		
NodeList resultNodehead = doc.getElementsByTagName("ss:Table");			
		
		System.out.println("following are data nodes in source xml");
		for(int i=0;i<resultNodehead.getLength();i++)
		{
			Node fstNode = resultNodehead.item(i);
			Element resultElement = (Element) fstNode;
			
			NodeList rows = resultElement.getElementsByTagName("ss:Row");			
			System.out.println("rows count::::::::::::::::::::::::::::::::::::::::::::::"+rows.getLength());
			for(int j=1;j<rows.getLength()&&j>rowRange;j++)
			{
				Node rowNode = rows.item(j);
				Element rowElement = (Element) rowNode;
				System.out.println("Hidden field for row :: "+j+" is "+rowElement.getAttribute("ss:Hidden"));
				if(!rowElement.getAttribute("ss:Hidden").equals("1")){
				NodeList cells = rowElement.getElementsByTagName("ss:Cell");
				System.out.println("In if Condition");
				for(int k=0;k<cells.getLength()&&k<colRange;k++)
				{
					Node cellNode = cells.item(k);
					Element cellElement = (Element) cellNode;
					
					NodeList datas = cellElement.getElementsByTagName("ss:Data");
					
					List<String> valSetOne = new ArrayList<String>();
					
					//System.out.print("rows "+ j + " columns " + k + " ");
					
					if(datas.getLength()==0)
					{
						String rowno = "row no: " + j;
						String colno = "column no: " + k;
						String s= "";
						valSetOne.add(rowno);	
						valSetOne.add(colno);
						valSetOne.add(s);
					
						String skeys = rowno+" "+colno;						
						
						map.put(skeys, valSetOne);
					}
					else
					{
								
						for(int l=0;l<datas.getLength();l++)
						{						
						
							Node dataNode = datas.item(l);
					 
							Element dataElement = (Element) dataNode;
							
							String rowno = "row no: " + j;
							String colno = "column no: " + k;
							
							valSetOne.add(rowno);	
							valSetOne.add(colno);
							valSetOne.add(dataElement.getTextContent().toString());												
							
							String skeys = rowno+" "+colno;
												
							map.put(skeys, valSetOne);

					 
						}
					}
				}
				
			}}
		}
		
NodeList resultNodehead1 = doc.getElementsByTagName("ss:Table");			
		
		System.out.println("following are data nodes in source xml");
		for(int i=0;i<resultNodehead1.getLength();i++)
		{
			Node fstNode = resultNodehead1.item(i);
			Element resultElement = (Element) fstNode;
			
			NodeList rows = resultElement.getElementsByTagName("Row");			
			
			for(int j=0;j<rows.getLength()&&j>rowRange;j++)
			{
				Node rowNode = rows.item(j);
				Element rowElement = (Element) rowNode;
				
				NodeList cells = rowElement.getElementsByTagName("Cell");
				
				for(int k=0;k<cells.getLength()&&k<colRange;k++)
				{
					Node cellNode = cells.item(k);
					Element cellElement = (Element) cellNode;
					
					NodeList datas = cellElement.getElementsByTagName("Data");
					
					List<String> valSetOne = new ArrayList<String>();
					
					//System.out.print("rows "+ j + " columns " + k + " ");
					
					if(datas.getLength()==0)
					{
						String rowno = "row no: " + j;
						String colno = "column no: " + k;
						String s= "";
						valSetOne.add(rowno);	
						valSetOne.add(colno);
						valSetOne.add(s);
					
						String skeys = rowno+" "+colno;						
						
						map.put(skeys, valSetOne);
					}
					else
					{
								
						for(int l=0;l<datas.getLength();l++)
						{						
						
							Node dataNode = datas.item(l);
					 
							Element dataElement = (Element) dataNode;
							
							String rowno = "row no: " + j;
							String colno = "column no: " + k;
							
							valSetOne.add(rowno);	
							valSetOne.add(colno);
							valSetOne.add(dataElement.getTextContent().toString());												
							
							String skeys = rowno+" "+colno;
												
							map.put(skeys, valSetOne);

					 
						}
					}
				}
				
			}
		}

		NodeList resultNode = doc.getElementsByTagName("Table");			
		
		System.out.println("following are data nodes in source xml");
		for(int i=0;i<resultNode.getLength();i++)
		{
			Node fstNode = resultNode.item(i);
			Element resultElement = (Element) fstNode;
			
			NodeList rows = resultElement.getElementsByTagName("Row");			
			
			for(int j=0;j<rows.getLength()&&j>rowRange;j++)
			{
				Node rowNode = rows.item(j);
				Element rowElement = (Element) rowNode;
				
				NodeList cells = rowElement.getElementsByTagName("Cell");
				
				for(int k=0;k<cells.getLength()&&k<colRange;k++)
				{
					Node cellNode = cells.item(k);
					Element cellElement = (Element) cellNode;
					
					NodeList datas = cellElement.getElementsByTagName("Data");
					
					List<String> valSetOne = new ArrayList<String>();
					
					//System.out.print("rows "+ j + " columns " + k + " ");
					
					if(datas.getLength()==0)
					{
						String rowno = "row no: " + j;
						String colno = "column no: " + k;
						String s= "";
						valSetOne.add(rowno);	
						valSetOne.add(colno);
						valSetOne.add(s);
					
						String skeys = rowno+" "+colno;						
						
						map.put(skeys, valSetOne);
					}
					else
					{
								
						for(int l=0;l<datas.getLength();l++)
						{						
						
							Node dataNode = datas.item(l);
					 
							Element dataElement = (Element) dataNode;
							
							String rowno = "row no: " + j;
							String colno = "column no: " + k;
							
							valSetOne.add(rowno);	
							valSetOne.add(colno);
							valSetOne.add(dataElement.getTextContent().toString());												
							
							String skeys = rowno+" "+colno;
												
							map.put(skeys, valSetOne);

					 
						}
					}
				}
				
			}
		}
		

		
//		for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
//			
//			Integer key = entry.getKey();
//		
//			List<String> values = entry.getValue();
//		
//			System.out.println("Key = " + key);
//		
//			System.out.println("Values = " + values );
//		
//			}	
		
		return map;
	
	}
	
	public static Boolean compareResult(String fil1,String expfil1, int rowRange, int colRange) throws SAXException, IOException, ParserConfigurationException
	{
		
		Path p = Paths.get(expfil1);
		String file = p.getFileName().toString();
		
		System.out.println(file);
		
		
		//fil = new File("/Users/automationuser/Desktop/RT_CC_GLData_Actuals_AdhocReport1.xml");	
		//File fil = new File(fil1);
		
		String resultspath = System.getProperty("user.dir")+"/TestLogs/Compare Results/";
		
		String targetfilepathresults = resultspath+"target"+file+HA.Utilities.File.GetDateTime()+".xml";
        java.io.FileWriter fw = new java.io.FileWriter(targetfilepathresults);
        fw.write(fil1);
        fw.close();
        
        System.out.println(targetfilepathresults);
        
        File fil = new File(targetfilepathresults);
		
        /**
        
		java.io.FileWriter fw = new java.io.FileWriter("my-file.xml");
	    fw.write(fil1);
	    fw.close();
	    
	    File fil = new File(System.getProperty("user.dir")+"\\my-file.xml");
	    
	    **/
	    
//	    File fil = new File("/Users/automationuser/Desktop/hataf/source/my-file.xml");
		
		LinkedHashMap<String, List<String>> map=Xmlcomparison.xmlreading(fil, rowRange, colRange);
		
		
		
		//expfil = new File("/Users/automationuser/Desktop/RT_CC_GLData_Actuals_AdhocReport10.xml");	
		
		logApp.logger.info(expfil1);
		File expfil = new File(expfil1);
		LinkedHashMap<String, List<String>> expmap=Xmlcomparison.xmlreading(expfil, rowRange, colRange);
		
	     
	    MapDifference<String, List<String>> diff = Maps.difference(map,expmap);
	     

		
	  System.out.println("**********************************************************************************************");

	  System.out.println("**********************************************************************************************");

	  
	  LinkedHashMap<String, List<String>> mapps = new LinkedHashMap<String, List<String>>();
	  
	  
	  
	  
		for (String key : map.keySet()) 
		{
			List<String> values = map.get(key);
			String src = values.get(2);
			String expsrcc= null;
			List<String> expvalues = expmap.get(key);
			try{
				expsrcc = expvalues.get(2);
			}
			catch(NullPointerException e){
				expsrcc = null;
			}
			if(src.equalsIgnoreCase(expsrcc))
			{
				
			}
			else
			{
				List<String> valSetOne = new ArrayList<String>();
				//System.out.println(key);
				for(String s:values)
				{
					//System.out.print(s+ " ");
					valSetOne.add(s);
				}
				//System.out.println();
//				for(String s:expvalues)
//				{
//					System.out.print(s+ " ");
//				}
				try{
					valSetOne.add(expvalues.get(2));
				}
				catch(NullPointerException e)
				{}
				//System.out.println();
				mapps.put(key, valSetOne);
			}
			
			
		}
		
		
		System.out.println("**********************************************************************************************");
		  Map<String, List<String>> rightm = diff.entriesOnlyOnRight();
		  
		  for (String key : rightm.keySet()) 
		  {
//			  System.out.println(key + " ");
//			  System.out.println(rightm.get(key));
			  List<String> rm = new ArrayList<String>();
			  
			  List<String> ml = rightm.get(key);
			  
			  for(int i=0;i<ml.size()-1;i++)
			  {
				  rm.add(ml.get(i));
			  }
			  
			  rm.add("data tag not there");
			  rm.add(ml.get(2));			  
			  
			 
			  	  
			  mapps.put(key, rm);
			  
		  }
		  
		  
		  System.out.println("**********************************************************************************************");  
		  
		  Map<String, List<String>> leftm = diff.entriesOnlyOnLeft();
		  
		  for (String key : leftm.keySet()) 
		  {
			  //System.out.println(key + " ");
			 // System.out.println(rightm.get(key));
			  List<String> lm =  leftm.get(key);
			  lm.add("data tag not there");
			  
			  mapps.put(key, lm);
			  
		  }
		  
		
		/****************************************************************/
		
	
		
		System.out.println("**********************************************************************************************");
		
		 if(mapps.size()==0)
	        {
	        	System.out.println("Verification is successful");
	        }
	        else
	        {
	        	System.out.println("Verification not successull writing changes to excel in testlogs");
	        	
	        	String compareresults = System.getProperty("user.dir")+"/TestLogs/Compare Results/";
				String strDate = HA.Utilities.HTML.GetDateTime();
				String targetfilepath = compareresults+file+strDate+".xlsx";
//				String targetfilepath = compareresults+strDate+".xlsx";
				
				
	        	//File excel = new File("/Users/automationuser/Desktop/poi-test.xlsx");
				
				File excel = new File(targetfilepath);
				Workbook wb = new XSSFWorkbook();
				//String filename = "/Users/automationuser/Desktop/poi-test.xlsx";
				String filename = targetfilepath;
				FileOutputStream fout = new FileOutputStream(filename);
				String sheetName = "Sheet1";
				wb.createSheet(sheetName) ;
				wb.write(fout);
				//wb.close();
				fout.close();			 
				 
			
				FileInputStream fis = new FileInputStream(excel);
				XSSFWorkbook book = new XSSFWorkbook(fis);
				XSSFSheet sheet = book.getSheet(sheetName);
	        
				Set<String> newRows = mapps.keySet();
				 //int rownum = sheet.getLastRowNum();
				int cellnum0 =0;
				Row row0 = sheet.createRow(0);        
	        	Cell cell0 = row0.createCell(cellnum0++);
	        	cell0.setCellValue("Row number");
	        
	        	cell0 = row0.createCell(cellnum0++);
	        	cell0.setCellValue("Column number");
	        
	        	cell0 = row0.createCell(cellnum0++);
	        	cell0.setCellValue("Souce value");
	        
	        	cell0 = row0.createCell(cellnum0++);
	        	cell0.setCellValue("Destination value");
	        
	        	int rownum = sheet.getLastRowNum()+1;
	        
	        

	        	for (String key : newRows) 
	        	{
	        		Row row = sheet.createRow(rownum++);
	        		List<String> objArr = mapps.get(key);
	        		int cellnum = 0;
	        		for (Object obj : objArr) 
	        		{
	        			Cell cell = row.createCell(cellnum++);
	        			if (obj instanceof String) 
	        			{
	        				cell.setCellValue((String) obj);
	        			} 
	        			else if (obj instanceof Boolean) 
	        			{
	        				cell.setCellValue((Boolean) obj);
	        			}
	        			else if (obj instanceof Date) 
	        			{
	                    cell.setCellValue((Date) obj);
	        			} 
	        			else if (obj instanceof Double) 
	        			{
	                    cell.setCellValue((Double) obj);
	        			}
	        		}
	        	}
	        
	        	// open an OutputStream to save written data into Excel file
	        	FileOutputStream os = new FileOutputStream(excel);
	        	book.write(os);
	        	System.out.println("Writing on Excel file Finished ...");
	        	

	        	// Close workbook, OutputStream and Excel file to prevent leak
	        	os.close();
	        	//book.close();
	        	fis.close();
	        }

		
        if(mapps.size()==0)
        {
        	return true;
        }
        else
        {
        	return false;
        }

        
		
	}

}
