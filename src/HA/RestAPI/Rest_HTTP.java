package HA.RestAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.util.SystemOutLogger;
import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Rest_HTTP {
	public static void main(String[] args) throws ClientProtocolException, IOException, ParseException {
		HttpClient client = new DefaultHttpClient();
		//HttpGet request = new HttpGet("http://www.thomas-bayer.com/sqlrest/CUSTOMER/1/");
		//HttpGet request = new HttpGet("https://www.googleapis.com/customsearch/v1?key=INSERT_YOUR_API_KEY&cx=017576662512468239146:omuauf_lfve&q=lectures");
		//HttpGet request = new HttpGet("http://validate.jsontest.com/?json=%5BJSON-code-to-validate%5D");
		//HttpGet request = new HttpGet("http://api.geonames.org/postalCodeSearchJSON?postalcode=9011&maxRows=5&username=demo");
		HttpGet request = new HttpGet("http://demo5861795.mockable.io/restput");
		
		HttpResponse response = client.execute(request);
		System.out.println("Response Code:"+response.getStatusLine().getStatusCode());
		System.out.println(response.getEntity().getContentType());

		PrintWriter writer = new PrintWriter(System.getProperty("user.dir")+"\\src\\HA\\TestData\\Output"+".JSON", "UTF-8");

		if(response.getStatusLine().getStatusCode()!=404){
			String line = "";
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			while ((line = rd.readLine()) != null) {
				//System.out.println(line);
				writer.println(line);
			}
			writer.close();
		}
		else
			System.out.println("Response Code:"+response.getStatusLine().getStatusCode());

		JSONParser parser = new JSONParser();
		File file=new File(System.getProperty("user.dir")+"\\src\\HA\\TestData\\Input.JSON");
		FileReader fileReader = new FileReader(file); 
		JSONObject json = (JSONObject) parser.parse(fileReader);
		System.out.println("input Json..... \n"+json);
		
		File fileout=new File(System.getProperty("user.dir")+"\\src\\HA\\TestData\\Output.JSON");
		FileReader fileReaderout = new FileReader(fileout); 
		JSONObject jsonout = (JSONObject) parser.parse(fileReaderout);
		System.out.println("Response Json..... \n"+jsonout);
		
		//==============Comparing two json files===============================
		if(json.toJSONString().equals(jsonout.toJSONString()))
			System.out.println("\nActual response matched with expected response");
		else
			System.out.println("\nData difference between expected & actual");
		
	}
}
