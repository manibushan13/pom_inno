package HA.Utilities;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;



import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;


public class CompareExcelSheets {

	
	
	public static boolean compare(String file11,String file21) throws Exception {
		File file1=new File(file11);
		File file2=new File(file21);
		InputStream book1 = new FileInputStream(file1);
		XSSFWorkbook wb = new XSSFWorkbook(book1);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		String curDir = System.getProperty("user.dir");
	
		System.out.println("Current abs dir: " + file2.getAbsolutePath());
		System.out.println("Current sys dir: " + curDir);
		InputStream book2 = new FileInputStream(file2);
		XSSFWorkbook wb1 = new XSSFWorkbook(book2);
	    

	/*	 System.out.println(wb1.getCustomXMLMappings()+ "   "	+wb1.getNumberOfSheets());
		for(XSSFMap map : wb1.getCustomXMLMappings())
		{
			XSSFExportToXml exporter = new XSSFExportToXml(map);
			OutputStream writer = new FileOutputStream("C:/hataf-main/hataf/source/src/HA/TestData/Reporting/Downloads/testing.xml"); 
			exporter.exportToXML(writer, false);
	       System.out.println("Wwriting");
		}*/
		XSSFSheet sheet2 = wb1.getSheetAt(0);

		for (int i = 0; i < sheet1.getLastRowNum()
				&& i < sheet2.getLastRowNum(); i++) {
			Row row1 = sheet1.getRow(i);
			Row row2 = sheet2.getRow(i);

			for (int j = 0; j < row1.getLastCellNum()
					&& j < row2.getLastCellNum(); j++) {
				String value1=getValue(row1,j);
				System.out.println(value1);
				String value2=getValue(row2,j);
				System.out.println(value2);
				if(value1.equals(value2))
			   {
				   
			   }
			   else
			   {
			   return false;
			   }
			}
		}
		return true;
	}
	public static void difference(String file11,String file21) throws Exception {
		File file1=new File(file11);
		File file2=new File(file21);
		InputStream book1 = new FileInputStream(file1);
		XSSFWorkbook wb = new XSSFWorkbook(book1);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		String curDir = System.getProperty("user.dir");
	
		System.out.println("Current abs dir: " + file2.getAbsolutePath());
		System.out.println("Current sys dir: " + curDir);
		InputStream book2 = new FileInputStream(file2);
		XSSFWorkbook wb1 = new XSSFWorkbook(book2);
		XSSFSheet sheet2 = wb1.getSheetAt(0);

		for (int i = 0; i < sheet1.getLastRowNum()
				&& i < sheet2.getLastRowNum(); i++) {
			Row row1 = sheet1.getRow(i);
			Row row2 = sheet2.getRow(i);

			for (int j = 0; j < row1.getLastCellNum()
					&& j < row2.getLastCellNum(); j++) {
				String value1=getValue(row1,j);
				String value2=getValue(row2,j);
				if(value1.equals(value2))
			   {
				   
			   }
			   else
			   {
	             System.out.println("Row:"+i+ "Col:" +j+"Source value:" +value1 + " Target value:" +value2);		   
			   }
			}
		}
	
	}
	public static String getValue(Row row, int y){
	   
		
	    org.apache.poi.ss.usermodel. Cell cell = row.getCell(y);
	    if(cell==null) 
	    	return "";
	    int type = cell.getCellType();
	    switch(type){
	    case 0:
	        return cell.getNumericCellValue() + "";
	    case 1:
	        return cell.getStringCellValue();
	    case 2:
	        return cell.getCellFormula();
	    case 3:
	        return "";
	    case 4:
	        return cell.getBooleanCellValue() + "";
	    case 5:
	        return cell.getErrorCellValue() + "";
	    default:
	        return "";
	    }
	}
	public static boolean compareImage(String file1, String file2) {        
	    try {
	    	File fileA=new File(file1);
	    	File fileB=new File(file2);
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
	        return  false;
	    }
	}
	public static boolean compareAllSheets(String file11,String file21) throws Exception {
		File file1=new File(file11);
		File file2=new File(file21);
		InputStream book1 = new FileInputStream(file1);
		XSSFWorkbook wb = new XSSFWorkbook(book1);
		int size1=wb.getNumberOfSheets();
		InputStream book2 = new FileInputStream(file2);
		XSSFWorkbook wb1 = new XSSFWorkbook(book2);
		int size2=wb1.getNumberOfSheets();		
		
		if(size1!=size2)
		{
		System.out.println("Number of Sheets are not equal");
			return false;
		
		}else
		{
			System.out.println("Number of Sheets are equal");	
		for(int k=0;k<size1;k++)
		{
		XSSFSheet sheet1 = wb.getSheetAt(k);
		System.out.println("SHEET1 :"+k+"- "+sheet1.getSheetName());
		XSSFSheet sheet2 = wb1.getSheetAt(k);
		System.out.println("SHEET2 :"+k+"- "+sheet2.getSheetName());
		for (int i = 0; i < sheet1.getLastRowNum() && i < sheet2.getLastRowNum(); i++) {
			
			Row row1 = sheet1.getRow(i);
			Row row2 = sheet2.getRow(i);

			if(row1!=null && row2!=null)
			{
			if(row1.getLastCellNum()!=row2.getLastCellNum())
			{
				System.out.println(sheet1+" Sheet size is not equal");
				return false;
			}
			for (int j = 0; j < row1.getLastCellNum()&& j < row2.getLastCellNum(); j++) {
				String value1=getValue(row1,j);
				String value2=getValue(row2,j);
				if(value1.equals(value2))
			   {
				   
			   }
			   else
			   {
				   System.out.println("Sheet:"+sheet1+" Row:"+i+" Col:"+j+" Data1 :"+value1+" Data2 :"+value2);
			   return false;
			   }
			}
		}else
		{
			System.out.println(i+"Encountered empty row");
			if(row1!=null || row2!=null)
				return false;
		}
		}
		
			
		}
		}
		return true;
	}
	public static boolean chromeComparision(String file11) throws Exception
	{
		File file1=new File(file11);
		InputStream book1 = new FileInputStream(file1);
		XSSFWorkbook wb = new XSSFWorkbook(book1);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		int startrow,startcol,endrow,endcol;
		startrow=20;
		startcol=14;
		endcol=107;
		endrow=sheet1.getLastRowNum();
		int var=0;
		for (int i = startrow; i < endrow; i++) {
			XSSFRow row1 = sheet1.getRow(i);
			for (int j = startcol; j < endcol; j++) {
				
				String value1=getValue(row1,j);
		            String value2="";  
				
				if(((JavascriptExecutor)HA.SeleniumLib.Common.dr).executeScript("var spread=top.frames[2].frames[2].spread;var sheet=spread.getSheetByName('Budget Sheet');var val=spread.getCellValue(sheet,"+i+","+j+");return val;")!=null)
					value2=((JavascriptExecutor)HA.SeleniumLib.Common.dr).executeScript("var spread=top.frames[2].frames[2].spread;var sheet=spread.getSheetByName('Budget Sheet');var val=spread.getCellValue(sheet,"+i+","+j+");return val;").toString();
			
			
				
				if(value1.equals(value2))
			   {
				   
			   }
			   else
			   {
				   if(j==28 ||j==29);
				   else
				   var++;
					System.out.println(i+" "+j+"excel "+value1);
					System.out.println(i+" "+j+"wijmo "+value2);
			   }
		
				
			}
		}
		if(var>0)
			return false;
		else
			return true;
	
		
		
		
		
	}	

}
