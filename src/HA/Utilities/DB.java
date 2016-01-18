package HA.Utilities;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import HA.TestAutomation.Driver;
import HA.TestAutomation.HATF_properties;

public class DB {
	
	public static HATF_properties _properties = new HATF_properties();
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs= null;
	static ResultSetMetaData rsmd = null;
	
	public static void conDB() throws ClassNotFoundException, SQLException{
		try{
			String db_connect_string=_properties.getProperty(HATF_properties.CONN_STR);
			Class.forName(_properties.getProperty(HATF_properties.SQLJAVADriver));
	        conn = DriverManager.getConnection(db_connect_string);
	        HA.TestAutomation.Driver.logger.info("DB Connection Success");
			stmt = conn.createStatement();
		}
		catch(Exception e){
			e.printStackTrace();
			Driver.logger.error(e);
		}
	}
	
	public static String QueryDB(String queryString) throws ClassNotFoundException, SQLException{
		String Horizon=null;
			
		try{
	
			conDB();
			//queryString = "Select PARAM_VAL from HOST_BPM_CONSTANTS WHERE PARAM_KEY='Horizon_DataLoad'";
			rs = stmt.executeQuery(queryString);
	        rsmd = rs.getMetaData();
	       // int dbcols = rsmd.getColumnCount();
	       
	       
	        while (rs.next()) {
	        	        	
	        	System.out.println(rs.getString(1));    		

	        	Horizon = rs.getString(1);
	        }
	         
		}
		catch(Exception e){
			e.printStackTrace();
			Driver.logger.error(e);
		}
		finally{
			 rs.close();                       // Close the ResultSet                  
		     stmt.close();   				   // Close the Statement
		}
		return Horizon;
		                             
	}
	
	public static String getDBdata(String query) throws ClassNotFoundException, SQLException{
		String Horizon=null;
			
		try{
	
			conDB();
			
			rs = stmt.executeQuery(query);
	        rsmd = rs.getMetaData();
	       // int dbcols = rsmd.getColumnCount();
	       
	        while (rs.next()) {	     	
	        	   		
	        	Horizon = rs.getString(1);
	        }
	         
		}
		catch(Exception e){
			e.printStackTrace();
			Driver.logger.error(e);
		}
		finally{
			 rs.close();                       // Close the ResultSet                  
		     stmt.close();   				   // Close the Statement
		}
		return Horizon;
		                             
	}
	
	public static void CompareDBdata() throws Exception{
		
		FileOutputStream  file = null;
		try{

			conDB();
			//String queryString = "Select * from HOST_BPM_CONSTANTS WHERE PARAM_KEY='Horizon_DataLoad'";
			//String queryString = "Select * from HOST_BPM_CONSTANTS";
			String queryString = "Select * from S_APP_VERSION";
			rs = stmt.executeQuery(queryString);
			rsmd = rs.getMetaData();
			int dbcols = rsmd.getColumnCount();
			String strDate = HTML.GetDateTime();

			String dbFilename = "Execution_Report_" + strDate+ ".xlsx";
			//String dbFilename = "Execution_Report.xlsx";
			String dbtargetpath = System.getProperty("user.dir") + "\\test-output\\"+ dbFilename;
			//file = new FileOutputStream(new File(dbtargetpath));   
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet sheet = wb.createSheet("DBQueryOutput");

			//XSSFSheet sheet = wb.createSheet(); 
			//wb.setSheetName(0, "DBQueryOutput");
			Row row = sheet.createRow(0);

			for(int i = 1;i<=dbcols;i++){
				System.out.println(rsmd.getColumnName(i));

				Cell cell = row.createCell(i-1);
				cell.setCellValue(rsmd.getColumnName(i));
				System.out.println(i);
			}

			int rowcount=1;
			while (rs.next()) {
				row =sheet.createRow(rowcount);
				for(int i = 1;i<=dbcols;i++){					 
					Cell cell = row.createCell(i-1);
					cell.setCellValue(rs.getString(i));					 
				}
				rowcount++;			           
			}
			file = new FileOutputStream(new File(dbtargetpath));

			wb.write(file);
		}
		catch(Exception e){
			e.printStackTrace();
			Driver.logger.error(e);
		}
		finally{

			file.close();
			rs.close();                       // Close the ResultSet                  
			stmt.close();
			// file.close();// Close the Statement
		}

		                             
	}

	public static void exeDB(String queryString) throws ClassNotFoundException, SQLException{
		try{
			conDB();			
			int rs  =stmt.executeUpdate(queryString);
			System.out.println(rs);
		}
		catch(Exception e){
			e.printStackTrace();
			Driver.logger.error(e);
		}
		finally{
//			rs.close();                       // Close the ResultSet                  
			stmt.close();   				   // Close the Statement
		}

	}
}
